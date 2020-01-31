package com.lagou.dao;

import com.lagou.annotation.Repository;
import com.lagou.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TracsferDaoImpl implements ITrancferDao {
    Connection connection;
    @Override
    public Integer modifyAccount(String userAccount, Double money) throws Exception{
        try {
            connection = ConnectionManager.init().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer result = 0;
        PreparedStatement preparedStatement = null;
        try {
            String sql = " update account set account = (account + ?) where useraccount = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,money);
            preparedStatement.setObject(2,userAccount);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            if(preparedStatement!=null){
                preparedStatement.close();
            }
        }
        return result;
    }
}
