package com.lagou.code.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient( name = "lagou-buss-email")
public interface EmailFeign {
    //@RequestMapping(value = "/email/{email}/{code}")
    //String email(@PathVariable("email")String email, @PathVariable("code") String code);
}
