package com.study.entity;

import lombok.Data;

@Data
public class Account {
    int id;     // 用户ID
    String username;        // 用户名
    String password;        // 密码
    String role;            // 角色

}
