package com.atguigu.crowdtest;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zhangchengwei
 * @create 2022-09-24 15:16
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

//    @Test
//    public void testInsert() {
//        Admin admin = new Admin(null, "tom", "123", "汤姆", "tom@qq.com", null);
//        int insert = adminMapper.insert(admin);
//        //获取Logger对象，这里传入的Class对象就是当前打印日志的类
//        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
//        logger.info("受影响的条数{}",insert);
//    }

    @Test
    public void test1() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

//    @Test
//    public void testInsert1() {
//        for (int i = 0;i < 60;i++) {
//            Admin admin = new Admin(null, "jerry", "12323", "杰瑞", "jerry@qq.com", null);
//            adminService.saveAdmin(admin);
//        }
//    }
}
