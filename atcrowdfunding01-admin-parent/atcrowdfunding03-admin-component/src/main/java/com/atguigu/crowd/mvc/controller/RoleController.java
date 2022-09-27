package com.atguigu.crowd.mvc.controller;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zhangchengwei
 * @create 2022-09-26 23:05
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "1") Integer pageSize,
                                                    @RequestParam(value = "keyword") String keyword) {
        // 1、查询数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

        // 2、封装到ResultEntity对象中返回，如果上面的操作抛出异常，交给异常处理机制处理
        return ResultEntity.successWithData(pageInfo);
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity<String> saveRole(Role role) {

        roleService.saveRole(role);

        return ResultEntity.successWithOutData();
     }

    /**
     * 更新角色
     *
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/update.json")
    public ResultEntity<String> updateRole(Role role) {

        roleService.updateRole(role);

        return ResultEntity.successWithOutData();
    }

    @ResponseBody
    @RequestMapping("/role/remove/by/role/id/array.json")
    public ResultEntity<String> removeRole(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRole(roleIdList);

        return ResultEntity.successWithOutData();
    }

}
