package com.atguigu.crowdtest;

import com.atguigu.crowd.util.CrowdUitl;
import org.junit.Test;

/**
 * @author zhangchengwei
 * @create 2022-09-25 18:29
 */
public class EncryptTest {

    @Test
    public void testString() {
        String password = "zcw1998";
        String s = CrowdUitl.md5Encrypt(password);
        System.out.println(s);
    }
}
