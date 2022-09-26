package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.util.CrowdUitl;
import com.atguigu.crowd.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhangchengwei
 * @create 2022-09-25 15:25
 */
//ControllerAdvice 表示当前类是一个基于注解的异常处理器类
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseForUpdateException exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName = "admin-edit";
        return commonResolve(viewName,exception,request,response);
    }

    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonResolve(viewName,exception,request,response);
    }

    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName,exception,request,response);
    }

    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveMathException(ArithmeticException exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName,exception,request,response);
    }

    // @ExceptionHandler将一个或多个具体的异常类型和一个方法关联起来
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerExcetion(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName,exception,request,response);
    }

    private ModelAndView commonResolve(String viewName,Exception exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        //1、判断请求类型
        boolean isAjax = CrowdUitl.judgeRequestType(request);
        if (isAjax) {
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            //创建gson对象
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            //将JSON字符串响应给浏览器
            response.getWriter().write(json);
            return null;
        }
        //如果不是Ajax请求
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        //设置对应的视图
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}


