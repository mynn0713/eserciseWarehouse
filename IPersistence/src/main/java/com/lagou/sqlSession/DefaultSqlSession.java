package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStreamMap().get(statementId);
        List<E> list = simpleExecutor.query(configuration, mappedStatement, params);
        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        List<Object> objects = selectList(statementId, params);
        if(objects.size() == 1){
            return (T) objects.get(0);
        }else{
            throw new RuntimeException("查询结果不为一条");
        }
    }
    @Override
    public Integer insertObj(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStreamMap().get(statementId);
        return simpleExecutor.insert(configuration, mappedStatement, params);
        //return insertObj(statementId, params);
    }

    @Override
    public <T> T getMappper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String key = className + "." + methodName;
                if(methodName.contains("select")) {
                    Type genericReturnType = method.getGenericReturnType();
                    if (genericReturnType instanceof ParameterizedType) {
                        return selectList(key, args);
                    }
                    return selectOne(key,args);
                }
                return insertObj(key, args);
            }
        });
        return (T) proxyInstance;
    }
}
