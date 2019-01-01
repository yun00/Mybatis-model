package com.test.mybatis.session;

import java.util.List;

public interface SqlSession {

    <T> T selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement,Object paramter);

    <T> T getMapper(Class<T> type);
}
