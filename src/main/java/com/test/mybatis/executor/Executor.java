package com.test.mybatis.executor;

import com.test.mybatis.config.MappedStatement;

import java.util.List;

public interface Executor {

    <E> List<E> query(MappedStatement ms,Object parameter);
}
