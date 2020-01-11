package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 该方法就是使用dom4j将配置文件解析，封装到Configuration对象中
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }
        //c3p0连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("userName"));
        comboPooledDataSource.setPassword(properties.getProperty("passWord"));
        configuration.setDataSource(comboPooledDataSource);

        List<Element> mapperlist = rootElement.selectNodes("//mapper");
        for (Element element : mapperlist) {
            String resource = element.attributeValue("resource");
            InputStream mapperResource = Resources.getResourceAsStream(resource);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(mapperResource);
        }

        return configuration;
    }
}
