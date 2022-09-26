package com.atguigu.crowd.mvc.controller;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author zhangchengwei
 * @create 2022-09-25 20:22
 */
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 登录方法
     *
     * @param loginAcct
     * @param userPswd
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session) {
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);
        session.setAttribute(CrowdConstant.ATRR_NAME_LOGIN_ADMIN,admin);
        //重定向到主页面：避免跳转到主页面后刷新重新提交表单（直接配置View-Controller）
        return "redirect:/admin/to/main/page.html";
    }

    /**
     * 退出系统方法
     *
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/logout.html")
    public String doLogOut(HttpSession session) {
        //强制session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    /**
     * 分页查询
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(@RequestParam(value = "keyword",required = false) String keyword,
                              @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                              Model model) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        model.addAttribute(CrowdConstant.ATTR_NAME_PAGEINFO,pageInfo);

        return "admin-page";
    }

    /**
     * 删除用户方法
     *
     * @param id
     * @param pageNum
     * @param keyword
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/remove/{id}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("id") Integer id,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword) throws UnsupportedEncodingException {
        // 执行删除
        adminService.remove(id);
        //页面跳转
        //方案一：直接转发到admin-page.jsp会无法显示分页数据
        //return "admin-page";
        //方案二：转发到/admin/get/page.html地址，刷新页面会重复执行删除浪费性能
        //return "forward:/admin/get/page.html";
        //方案三：重定向到/admin/get/page.html地址
        //同时为了保持删除数据的页面和查询关键词再附加pageNum和keyword两个请求参数
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + URLEncoder.encode(keyword,"utf-8");
    }

    /**
     * 新增用户方法
     *
     * @param admin
     * @return
     */
    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {
        adminService.saveAdmin(admin);

        //跳转到最后一页
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    /**
     * 编辑用户信息前查询用户信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/admin/to/edit/page.html")
    public String editPage(@RequestParam("adminId") Integer id, Model model) {
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("admin",admin);
        return "admin-edit";
    }

    /**
     * 更新用户信息
     *
     * @param admin
     * @param pageNum
     * @param keyword
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/admin/update.html")
    public String updatePage(Admin admin, @RequestParam("pageNum") Integer pageNum,@RequestParam("keyword") String keyword) throws UnsupportedEncodingException {
        adminService.update(admin);

        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + URLEncoder.encode(keyword,"utf-8");
    }
}
