package com.test.mybatis.executor;

import com.test.mybatis.config.Configuration;
import com.test.mybatis.config.MappedStatement;

import java.util.List;

public class DefaultExecutor implements Executor {
    private Configuration configuration;

    public DefaultExecutor(Configuration configuration){
        this.configuration = configuration;
}

    public <E> List<E> query(MappedStatement ms, Object parameter) {
        System.out.println(ms.getSql());
        return null;
    }
}
