package com.atguigu.crowd.mvc.controller;

import com.atguigu.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhangchengwei
 * @create 2022-09-26 23:05
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;
}
