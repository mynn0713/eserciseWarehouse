package com.lagou.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private DataSource dataSource;
    /**
     *  key是namespace.id value是封装好的mappedStreamMap对象
     */
    private Map<String, MappedStatement> mappedStreamMap = new HashMap<String, MappedStatement>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStreamMap() {
        return mappedStreamMap;
    }

    public void setMappedStreamMap(Map<String, MappedStatement> mappedStreamMap) {
        this.mappedStreamMap = mappedStreamMap;
    }
}
