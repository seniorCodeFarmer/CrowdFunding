package com.atguigu.crowd.util;

import com.atguigu.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

/**
 * @author zhangchengwei
 * @create 2022-09-25 15:09
 */
public class CrowdUitl {

    /**
     * 判断当前请求是否为Ajax请求
     * @param request
     * @return true 当前请求是Ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1、获取请求消息头
        String accept = request.getHeader(CrowdConstant.ACCEPT);
        String xRequestedWith = request.getHeader(CrowdConstant.X_REQUESTED_WITH);

        // 2、判断
        return accept != null && accept.contains("application/json") || xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest");
    }

    /**
     * 对明文字符串进行 MD5加密
     * @param source 明文
     * @return 加密结果
     */
    public static String md5Encrypt(String source) {
        // 1、判断source是否有效
        if (source == null || source.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATA);
        }

        // 2、获取MessageDigest对象
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 3、获取明文字符串对应的字节数组
            byte[] bytes = source.getBytes();
            // 4、执行加密
            byte[] digest = messageDigest.digest(bytes);

            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, digest);
            //5、按照16进制将Biginteger的值转为字符串
            int radix = 16;
            String encode = bigInteger.toString(radix);
            return encode.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
