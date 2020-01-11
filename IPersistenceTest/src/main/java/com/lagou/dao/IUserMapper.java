package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserMapper {
    List<User> selectList();

    Integer insertObj(User user);
}
