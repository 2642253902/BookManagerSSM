package com.study.service.impl;

import com.study.entity.Account;
import com.study.mapper.UserMapper;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = userMapper.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("未找到该用户名对应的用户。: " + username);  // 这里的异常信息可以根据需要进行调整
        }

        return User.withUsername(account.getUsername())  // 使用Spring Security提供的User类来构建UserDetails对象
                .password(account.getPassword())   // 设置用户的密码，这里假设数据库中存储的密码已经经过加密处理
                .roles(account.getRole())          // 设置用户的角色，这里假设数据库中的role字段存储的是用户的角色信息
                .build();                           // 构建并返回UserDetails对象
    }
}
