package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

import javax.xml.ws.soap.AddressingFeature;
import java.util.List;

/**
 * @author zhangchengwei
 * @create 2022-09-24 18:09
 */
public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct,String userPswd);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);
}
