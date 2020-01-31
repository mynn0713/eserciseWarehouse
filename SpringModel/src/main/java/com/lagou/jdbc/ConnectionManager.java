package com.lagou.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static ConnectionManager connectionManager ;
    private ConnectionManager(){}

    public static ConnectionManager init(){
        if(connectionManager == null){
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    public void createConnectionToThreanLocal(Properties properties) throws Exception{
        Connection connection = connectionThreadLocal.get();
        if(connection == null) {
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            connection = dataSource.getConnection();
        }
        connectionThreadLocal.set(connection);
        this.beginTransaction();
    }

    public Connection getConnection() throws Exception{
        Connection connection = connectionThreadLocal.get();
        if(connection != null) {
            return connection;
        }else{
            throw new Exception("无效的数据库连接");
        }
    }

    public void beginTransaction() throws Exception{
        Connection connection = connectionThreadLocal.get();
        if(connection!=null){
            connection.setAutoCommit(false);
        }else {
            throw new Exception("无效的数据库连接");
        }
    }
    public void commitTransaction() throws Exception{
        Connection connection = connectionThreadLocal.get();
        if(connection!=null){
            connection.commit();
        }else {
            throw new Exception("无效的数据库连接");
        }
    }
    public void rollbackTransaction() throws Exception{
        Connection connection = connectionThreadLocal.get();
        if(connection!=null){
            connection.rollback();
        }else {
            throw new Exception("无效的数据库连接");
        }
    }
    public void closeConnection()throws Exception{
        Connection connection = connectionThreadLocal.get();
        if(connection!=null){
            try {
                connection.close();
                connectionThreadLocal.remove();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
    }

}
