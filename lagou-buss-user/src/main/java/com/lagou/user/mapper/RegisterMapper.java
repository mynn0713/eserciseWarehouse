package com.lagou.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

//@Mapper
@Repository
public interface RegisterMapper {
    List<Map> selectUser();

    int insertUser(@Param("email") String email,@Param("token") String token);

    int selectCodeInExpiretTime(@Param("email") String email, @Param("code") String code);
}
