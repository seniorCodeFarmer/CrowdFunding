package com.atguigu.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author zhangchengwei
 * @create 2022-09-25 11:04
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String province;

    private String city;

    private String street;
}
