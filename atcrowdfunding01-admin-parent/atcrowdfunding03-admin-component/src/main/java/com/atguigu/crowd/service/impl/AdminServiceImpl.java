package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUitl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangchengwei
 * @create 2022-09-24 18:10
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public void saveAdmin(Admin admin) {
        // 1、加密密码
        String userPswd = admin.getUserPswd();
        userPswd = CrowdUitl.md5Encrypt(userPswd);
        admin.setUserPswd(userPswd);

        // 2、创建生成时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = simpleDateFormat.format(date);
        admin.setCreateTime(formatDate);

        // 3、执行保存
        try {
            adminMapper.insert(admin);
        } catch (Exception exception) {
            logger.info("异常信息{}" + exception.getClass().getName());
            if (exception instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {
        List<Admin> admins = adminMapper.selectByExample(new AdminExample());
        return admins;
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1、根据登录帐号查询Admin对象
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        // 2、判断Admin对象是否为Null
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (adminList.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGINACCT_NOT_UNIQUE);
        }

        Admin admin = adminList.get(0);

        // 3、如果Admin对象为Null则抛出异常
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4、如果Admin对象不为Null，则将数据库密码从Admin对象中取出
        String userPswdDB = admin.getUserPswd();

        // 5、将表单提交的明文密码进行加密
        String userPswdEncrypt = CrowdUitl.md5Encrypt(userPswd);

        // 6、对密码进行比较
        if (!Objects.equals(userPswdDB, userPswdEncrypt)) {
            // 7、如果比较结果不一致抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 8、如果一致则返回Admin对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1、调用pageHelper的静态方法开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        // 2、执行查询
        List<Admin> adminList = adminMapper.selectAdminByKeyWord(keyword);
        // 3、封装到pageInfo对象中
        return new PageInfo<>(adminList);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        //"Selective"表示有选择的更新，对于Null值不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception exception) {
            logger.info("异常信息{}" + exception.getClass().getName());
            if (exception instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelationShip(Integer adminId, List<Integer> roleList) {
        adminMapper.deleteRelationShip(adminId);

        if (roleList != null && roleList.size() > 0) {
            adminMapper.insertNewRelationShip(adminId,roleList);
        }
    }
}
