package com.lagou.tp.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @Author : maoying
 * @Date : 2020/5/13 13:21
 */
@Activate
public class TPMonitorFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        Long startTime = System.currentTimeMillis();
        System.out.println("远程调用开始："+startTime);
        try {
            result = invoker.invoke(invocation);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Long endTime = System.currentTimeMillis();
            System.out.println("远程调用结束："+endTime);
            Long consume = endTime - startTime;
            RpcContext.getContext().setAttachment("consume",consume.toString());
            System.out.println("总耗时："+consume+"ms");
            Long sumRrunTime = Long.valueOf(RpcContext.getContext().getAttachment("sumRrunTime")==null
                    ?"0":RpcContext.getContext().getAttachment("sumRrunTime"))+consume;
            RpcContext.getContext().setAttachment("sumRrunTime",sumRrunTime.toString());
        }

        return result;
    }
}
