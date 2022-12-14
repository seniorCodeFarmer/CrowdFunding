package com.atguigu.crowd.mvc.interceptor;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zhangchengwei
 * @create 2022-09-25 22:47
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 判断登录状态，如果已登录则放行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、通过request对象获取session对象
        HttpSession session = request.getSession();

        // 2、尝试从Session域中获取Admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATRR_NAME_LOGIN_ADMIN);

        // 3、判断Admin对象是否为空
        if (admin == null) {
            // 4、抛出异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }

        // 5、如果Admin对象不为空，则放行
        return true;
    }
}
