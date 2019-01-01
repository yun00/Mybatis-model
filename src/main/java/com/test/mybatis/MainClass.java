package com.test.mybatis;

import com.test.mybatis.Entity.User;
import com.test.mybatis.mapper.UserMapper;
import com.test.mybatis.session.SqlSession;
import com.test.mybatis.session.SqlSessionFactory;

import java.util.List;

public class MainClass {

    public static void main(String args[]){

        SqlSessionFactory factory = new SqlSessionFactory();

        SqlSession session = factory.openSession();

        UserMapper userMapper = session.getMapper(UserMapper.class);

        userMapper.selectByPrimarykey(1);

        List<User> userList = userMapper.selectAll();

    }
}
