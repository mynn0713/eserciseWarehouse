package com.lagou.sqlSession;

import com.lagou.config.XMLConfigBuilder;
import com.lagou.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory builder(InputStream in) throws DocumentException, PropertyVetoException {
        // 第1使用dom4j解析配置文件，将解析出来的内容封装到Configuation
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
        // 创建sqlSessionFactory对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);


        return defaultSqlSessionFactory;
    }
}
