package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
    public void parse(InputStream mapperResource) throws DocumentException {
        Document document = new SAXReader().read(mapperResource);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        parseFunciton(rootElement,namespace,"select");
        parseFunciton(rootElement,namespace,"insert");
    }

    private void parseFunciton(Element rootElement ,String namespace,String typeStr){
        List<Element> list = rootElement.selectNodes("//"+typeStr);
        for (Element element : list) {
            //id  resultType  paramsterType  sql
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramsterType = element.attributeValue("paramsterType");
            String sql = element.getTextTrim();
            String key = namespace+"."+id;
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParamterType(paramsterType);
            mappedStatement.setSql(sql);
            configuration.getMappedStreamMap().put(key,mappedStatement);
        }
    }
}
