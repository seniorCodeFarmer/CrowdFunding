package com.atguigu.crowd.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author zhangchengwei
 * @create 2022-09-25 20:05
 * 登录失败后抛出的异常
 */
public class LoginFailedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
