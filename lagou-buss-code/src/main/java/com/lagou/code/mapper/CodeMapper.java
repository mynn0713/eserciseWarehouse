package com.lagou.code.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeMapper {
    int insertCode(@Param("email")String email,@Param("code")String code,@Param("codeTimeout") Integer codeTimeout);
}
