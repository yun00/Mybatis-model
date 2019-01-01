package com.test.mybatis.session;

import com.test.mybatis.bending.MappedProxy;
import com.test.mybatis.config.Configuration;
import com.test.mybatis.config.MappedStatement;
import com.test.mybatis.executor.DefaultExecutor;
import com.test.mybatis.executor.Executor;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Proxy;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration conf;

    private Executor executor;

    public DefaultSqlSession(Configuration conf){
        this.conf = conf;
        this.executor = new DefaultExecutor(conf);
    }

    public <T> T selectOne(String statement, Object parameter) {
        List<T> selectList = this.selectList(statement, parameter);
        if(selectList == null || selectList.size()==0){
            return null;
        }
        if(selectList.size() ==1){
            return selectList.get(0);
        }
        throw new RuntimeException("too many result!");
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement ms = conf.getMappedStatementMap().get(statement);

        return executor.query(ms,parameter);
    }

    public <T> T getMapper(Class<T> type) {
        MappedProxy mp = new MappedProxy(this);
        return (T)Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},mp);
    }
}
