package com.lagou.edu.mapper;

import com.lagou.edu.pojo.User;

public interface LoginMapper {
    Integer findUserForLogin (User user);
}
