package com.lagou.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {
    //查询所有
    public <E> List<E> selectList(String statementId, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException;
    //查询一个
    public <T> T selectOne(String statementId, Object... params) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;
    //插入对象
    public Integer insertObj(String statementId, Object... params) throws Exception;
    //通过getMapper获取mapper接口的代理对象
    public <T> T getMappper(Class<?> mapperClass);
}
