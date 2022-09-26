package com.atguigu.crowd.exception;

/**
 * @author zhangchengwei
 * @create 2022-09-26 13:57
 *
 * 保存或更新admin时，登录帐号重复抛出异常
 */
public class LoginAcctAlreadyInUseException extends RuntimeException{
    public LoginAcctAlreadyInUseException() {
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
