package com.lagou.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDataSource {


    // 存放配置信息Map
    private static Map<String, String> configMap = new HashMap<>();
    private static CuratorFramework client;
    // 存放数据源
    private static ThreadLocal<ComboPooledDataSource> threadLocal = new ThreadLocal();
    // 节点path
    private static final String CONFIG_PREFIX = "/CONFIG";

    private static final String ZOOKEEPER_ADDRESS = "192.168.48.142:2181";



    /*
        获取连接
     */
    public static  Connection getConnection() {
        if (threadLocal.get()!=null) {
            try {
                return threadLocal.get().getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        log
        return null;
    }

    /*
        初始化方法
     */
    public static void init() throws Exception {
        client = CuratorFrameworkFactory.newClient(ZOOKEEPER_ADDRESS, new RetryNTimes(3, 1000));
        client.start();
        getConfig();
        initDateSource();
        startListener();
    }


    /*
        获取zk配置
     */
    private static void getConfig() throws Exception {

        List<String> childrenNames = client.getChildren().forPath(CONFIG_PREFIX);

        for (String childrenName : childrenNames) {

            String value = new String(client.getData().forPath(CONFIG_PREFIX + "/" + childrenName));
            configMap.put(childrenName,value);

        }
    }


    /*
     设置监听
  */
    private  static void startListener() throws Exception {


        PathChildrenCache watcher = new PathChildrenCache(client, CONFIG_PREFIX, true);
        watcher.getListenable().addListener(new PathChildrenCacheListener() {

            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                String path = event.getData().getPath();
                System.out.println("该节点数据产生变更："+new String(event.getData().getData()));

                if (path.startsWith(CONFIG_PREFIX)) {
                    String key = path.replace(CONFIG_PREFIX + "/", "");
                    // 子节点新增或变更时 更新缓存信息
                    if (PathChildrenCacheEvent.Type.CHILD_UPDATED.equals(event.getType())) {
                        System.out.println("更新配置信息...");
                        configMap.put(key, new String(event.getData().getData()));
                        // 释放之前连接池，创建新的连接池
                        if (threadLocal.get() !=null){
                            threadLocal.get().close();
                        }

                        initDateSource();
                        System.out.println("更新连接池..."+threadLocal.get().getJdbcUrl());
                    }

                }
            }
        });
        System.out.println("监听启动");
        watcher.start();


    }




    /*
        获取数据源
     */
    private static void initDateSource() throws PropertyVetoException, SQLException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(configMap.get("c3p0.driverClassName"));
        dataSource.setJdbcUrl(configMap.get("cc3p0.dbJDBCUrl"));
        dataSource.setUser(configMap.get("cc3p0.username"));
        dataSource.setPassword(configMap.get("c3p0.password"));
        threadLocal.set(dataSource);
    }





}
