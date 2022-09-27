package com.atguigu.crowd.mvc.controller;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhangchengwei
 * @create 2022-09-24 22:30
 */
@Controller
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm")
    public String testSsm(Model model, HttpServletRequest request) {
        List<Admin> list = adminService.getAll();
        model.addAttribute("list", list);
//        boolean b = CrowdUitl.judgeRequestType(request);
//        logger.info("是否是Ajax请求{}",b);
        System.out.println(10 / 0);
        return "target";
    }

    @RequestMapping("/send/array/one.html")
    @ResponseBody
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
        for (Integer integer : array) {
            System.out.println(integer);
        }
        return "success";
    }

//    @RequestMapping("/send/array/two.html")
//    @ResponseBody
//    public String testReceiveArrayTwo(@RequestParam("array[]") List<Integer> array) {
//        for (Integer integer : array) {
//            System.out.println(integer);
//        }
//
//    }

    @RequestMapping("/send/array/three.html")
    @ResponseBody
    public String testReceiveArrayThree(@RequestBody List<Integer> array) {
        for (Integer integer : array) {
            logger.debug("number:{}", integer);
        }

        return "success";
    }

//    @RequestMapping("/send/array/four.json")
//    @ResponseBody
//    public ResultEntity<Student> testReceiveArrayFour(@RequestBody Student student) {
//        logger.debug("student:{}", student.toString());
//        return ResultEntity.successWithData(student);
//    }
}
