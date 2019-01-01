package com.test.mybatis.bending;

import com.test.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

public class MappedProxy implements InvocationHandler {

    private SqlSession session;

    public MappedProxy(SqlSession sqlsession){
        super();
        this.session = sqlsession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Class<?> returnType = method.getReturnType();
        if(Collection.class.isAssignableFrom(returnType)){
            return session.selectList(method.getDeclaringClass().getName()+"."+method.getName(),args==null?null:args[0]);
        }
        return session.selectOne(method.getDeclaringClass().getName()+"."+method.getName(),args==null?null:args[0]);
    }
}
