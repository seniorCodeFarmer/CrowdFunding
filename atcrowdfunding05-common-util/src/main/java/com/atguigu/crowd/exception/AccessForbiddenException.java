package com.atguigu.crowd.exception;

/**
 * @author zhangchengwei
 * @create 2022-09-25 22:51
 *
 * 用户没有登录就访问受保护资源时抛出的异常
 */
public class AccessForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    protected AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
