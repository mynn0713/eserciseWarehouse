package com.lagou.server;

import com.lagou.rpc.RpcDecoder;
import com.lagou.rpc.RpcEncoder;
import com.lagou.rpc.RpcRequest;
import com.lagou.serializer.JSONSerializer;
import com.lagou.service.UserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    public String sayHello(String world) {
        System.out.println(new Date()+",成功接收参数------>"+world);
        return "sucess";
    }

    public static void startService(String hostName,int prot) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //pipeline.addLast(new StringEncoder());
                        //pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        try {
                            pipeline.addLast(new UserServerHandler());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        serverBootstrap.bind(hostName,prot).sync();
        System.out.println("服务启动");
    }
}
