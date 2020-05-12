package com.lagou.api.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Activate
public class TransportIPFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String localHost = context.getLocalHost();
        String remoteHost = context.getRemoteHost();
        System.out.println("localHost:"+localHost);
        System.out.println("remoteHost:"+remoteHost);


        Result result = invoker.invoke(invocation);
        return result;
    }
}
