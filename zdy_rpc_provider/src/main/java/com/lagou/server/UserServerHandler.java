package com.lagou.server;

import com.lagou.rpc.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@Component
public class UserServerHandler extends ChannelInboundHandlerAdapter implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断是否符合约定，符合则调用本地方法，返回数据
        // msg:  UserService#sayHello#are you ok?
        // (msg.toString().startsWith("UserService"))
        RpcRequest rpcRequest = (RpcRequest) msg;
        Object handler = this.handler(rpcRequest);
        System.out.println(new Date()+"执行成功----->"+handler);
        ctx.writeAndFlush(handler);
        System.out.println("返回调用结果，完成");
    }

    private Object handler(RpcRequest rpcRequest) throws ClassNotFoundException, InvocationTargetException {
        Class<?> aClass = Class.forName(rpcRequest.getClassName());
        Object serviceBean = applicationContext.getBean(aClass);
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        FastClass fastClass = FastClass.create(aClass);
        FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);
        return fastMethod.invoke(serviceBean,parameters);
    }

}
