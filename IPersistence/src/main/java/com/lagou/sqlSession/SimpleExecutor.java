package com.lagou.sqlSession;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {
        Connection connection = configuration.getDataSource().getConnection();
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);

        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0 ; i < parameterMappings.size() ; i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();

            Field declaredField = paramterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,o);
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = this.getClassType(resultType);
        ArrayList<Object> objects = new ArrayList<>();
        while(resultSet.next()) {
            Object o = resultTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            objects.add(o);
        }


        return (List<E>) objects;
    }
    @Override
    public Integer insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        Connection connection = configuration.getDataSource().getConnection();
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);

        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0 ; i < parameterMappings.size() ; i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();

            Field declaredField = paramterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,o);
        }

        int resultSet = preparedStatement.executeUpdate();
        return resultSet;
    }
    private Class<?> getClassType(String classType) throws ClassNotFoundException {
        if(classType!=null){
            Class<?> aClass = Class.forName(classType);
            return aClass;
        }
        return null;
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String sqlText = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(sqlText, parameterMappings);
        return boundSql;
    }
}
