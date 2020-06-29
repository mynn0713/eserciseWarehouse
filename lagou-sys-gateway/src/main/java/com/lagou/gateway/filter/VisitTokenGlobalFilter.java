package com.lagou.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Component
public class VisitTokenGlobalFilter implements GlobalFilter, Ordered {
    static HashMap<String,Integer> ipMap = new HashMap();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = request.getRemoteAddress().getAddress().getHostAddress();
        String jwtToken = request.getHeaders().getFirst("Authorization");
        System.out.println("ip:"+ip+",token:"+jwtToken);
        if(request.getPath().toString().indexOf("/api/user/register")>-1 ){
            if (ipMap.containsKey(ip)) {
                if (ipMap.get(ip) > 5) {
                    ipMap.remove(ip);
                    System.out.println("ip爆刷");
                    String url = "/api/user/badRegister";
                    ServerHttpResponse response = exchange.getResponse();
                    //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                    response.setStatusCode(HttpStatus.SEE_OTHER);
                    response.getHeaders().set(HttpHeaders.LOCATION, url);
                    //exchange.transformUrl(url);
                    return response.setComplete();
                }
                ipMap.put(ip, ipMap.get(ip) + 1);
            } else {
                ipMap.put(ip, 0);
            }
        }
        //return chain.filter(exchange);
        if(request.getPath().toString().indexOf("/api/user/loginPage")!=-1
            || request.getPath().toString().indexOf("/api/user/login")!=-1
            || request.getPath().toString().indexOf("/api/user/register")!=-1
            || request.getPath().toString().indexOf("/api/code/create")!=-1
            || request.getPath().toString().indexOf("/api/user/badRegister")!=-1){
            return chain.filter(exchange);
        }else if (jwtToken!=null && !"".equals(jwtToken)){
            System.out.println("token未丢失");
            return chain.filter(exchange);
        }else{
            System.out.println("token丢失");
            String url = "/api/user/loginPage";
            ServerHttpResponse response = exchange.getResponse();
            //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
            response.setStatusCode(HttpStatus.SEE_OTHER);
            response.getHeaders().set(HttpHeaders.LOCATION, url);
            //exchange.transformUrl(url);
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
