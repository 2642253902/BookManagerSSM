package com.study.mapper;

import com.study.entity.Account;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {
    @Select("select  * from  user where  username =#{username}")
    Account findByUsername(String username);    // 根据用户名查询用户信息



}
