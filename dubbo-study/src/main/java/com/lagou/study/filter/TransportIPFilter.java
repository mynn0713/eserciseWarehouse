package com.lagou.study.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Activate(
        group = {"provider", "consumer"}
)
public class TransportIPFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String localHost = context.getLocalHost();
        String remoteHost = context.getRemoteHost();
        String requetIp = context.getAttachment("requestIp");
        System.out.println("localHost:"+localHost);
        System.out.println("remoteHost:"+remoteHost);
        System.out.println("requetIp:"+requetIp);


        Result result = invoker.invoke(invocation);
        return result;
    }
}
