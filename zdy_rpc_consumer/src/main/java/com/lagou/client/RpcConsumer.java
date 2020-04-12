package com.lagou.client;

import com.lagou.rpc.RpcDecoder;
import com.lagou.rpc.RpcEncoder;
import com.lagou.rpc.RpcRequest;
import com.lagou.serializer.JSONSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcConsumer {
    //创建线程池对象
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static UserClientHandler userClientHandler;
    //1.创建一个代理对象 providerName：UserService#sayHello are you ok?
    public Object createProxy(Class<?> serviceClass){
        //借助JDK动态代理生成代理对象
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //（1）调用初始化netty客户端的方法
                if(userClientHandler == null){
                    initClient();
                }
                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setRequestId(UUID.randomUUID().toString().replaceAll("-",""));
                rpcRequest.setClassName(method.getDeclaringClass().getName());
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setParameterTypes(method.getParameterTypes());
                rpcRequest.setParameters(args);
                // 设置参数
                userClientHandler.setPara(rpcRequest);

                System.out.println(rpcRequest);
                System.out.println("设置参数完成");
                // 去服务端请求数据
                return executor.submit(userClientHandler).get();
            }
        });
    }

    //2.初始化netty客户端
    public static void initClient() throws InterruptedException {
        userClientHandler = new UserClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //pipeline.addLast(new StringEncoder());
                        //pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(userClientHandler);
                    }
                });
        bootstrap.connect("127.0.0.1",8990).sync();
    }
}
