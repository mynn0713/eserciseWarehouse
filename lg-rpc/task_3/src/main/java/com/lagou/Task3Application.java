package com.lagou;

import com.lagou.config.CustomerDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;

@SpringBootApplication
public class Task3Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Task3Application.class, args);

        CustomerDataSource.init();
        Connection connection = CustomerDataSource.getConnection();
        System.out.println(connection);


    }

}
