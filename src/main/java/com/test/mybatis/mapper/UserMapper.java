package com.test.mybatis.mapper;

import com.test.mybatis.Entity.User;

import java.util.List;

public interface UserMapper {

    public User selectByPrimarykey(Integer integer);

    public List<User> selectAll();
}
