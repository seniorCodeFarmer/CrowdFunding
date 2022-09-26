package com.atguigu.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author zhangchengwei
 * @create 2022-09-25 11:06
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Subject {

    private String subjectName;

    private Integer subjectScore;
}
