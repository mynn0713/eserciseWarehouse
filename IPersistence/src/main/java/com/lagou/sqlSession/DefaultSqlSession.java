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
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStreamMap().get(statementId);
        List<E> list = simpleExecutor.query(configuration, mappedStatement, params);
        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
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
    public Integer updateById(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStreamMap().get(statementId);
        return simpleExecutor.updateById(configuration, mappedStatement, params);
    }

    @Override
    public Integer deleteById(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStreamMap().get(statementId);
        return simpleExecutor.deleteById(configuration, mappedStatement, params);
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
                }else if(methodName.contains("insert")) {
                    return insertObj(key,args);
                }else if(methodName.contains("update")) {
                    return updateById(key,args);
                }else if(methodName.contains("delete")) {
                    return deleteById(key,args);
                }else{
                    throw new Exception("未找到该标签的实现方法");
                }
            }
        });
        return (T) proxyInstance;
    }
}
