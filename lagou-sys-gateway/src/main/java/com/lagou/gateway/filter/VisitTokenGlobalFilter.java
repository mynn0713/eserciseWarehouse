package com.lagou.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class VisitTokenGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = request.getRemoteAddress().getAddress().getHostAddress();
        String jwtToken = request.getHeaders().getFirst("Authorization");
        System.out.println("ip:"+ip+",token:"+jwtToken);
        return chain.filter(exchange);
        /*if (jwtToken!=null & "".equals(jwtToken))
        {
            System.out.printf("token未丢失");
            return chain.filter(exchange);
        }
        else
        {
            System.out.printf("token丢失");
            //请求超时
            exchange.getResponse().setStatusCode(HttpStatus.REQUEST_TIMEOUT);
            return exchange.getResponse().setComplete();
        }*/
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
