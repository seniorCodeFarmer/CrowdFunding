package com.atguigu.crowd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admin {
    private Integer id;

    private String loginAcct;

    private String userPswd;

    private String userName;

    private String email;

    private String createTime;
}