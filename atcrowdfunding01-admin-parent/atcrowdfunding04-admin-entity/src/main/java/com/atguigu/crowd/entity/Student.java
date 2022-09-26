package com.atguigu.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author zhangchengwei
 * @create 2022-09-25 11:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

    private Integer stuId;

    private String stuName;

    private Address address;

    private List<Subject> subjectList;

    private Map<String,String> map;
}
