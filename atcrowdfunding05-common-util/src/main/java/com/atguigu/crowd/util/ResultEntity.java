package com.atguigu.crowd.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author zhangchengwei
 * @create 2022-09-25 11:36
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity<T> {

    private static final String SUCCESS = "SUCCESS";

    private static final String FAILED = "FAILED";

    //响应码
    private String resultCode;

    //请求处理失败时返回的错误信息
    private String message;

    private T data;

    /**
     * 请求处理成功且不需要返回数据时使用的方法
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithOutData() {
        return new ResultEntity<Type>(SUCCESS,null,null);
    }

    /**
     * 请求处理成功且需要返回数据时使用的方法
     * @param data
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data) {
        return new ResultEntity<Type>(SUCCESS,null,data);
    }

    /**
     * 请求处理失败且需要返回失败消息
     * @param message
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(String message) {
        return new ResultEntity<Type>(FAILED,message,null);
    }
}
