package com.lagou.filter;

import com.lagou.util.MonitorWaterLineCalculator;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author : maoying
 * @Date : 2020/5/14 9:22
 */
@Activate(group = {CommonConstants.CONSUMER,CommonConstants.PROVIDER})
public class TPMonitorFilter implements Filter {

    static MonitorWaterLineCalculator monitor90 = new MonitorWaterLineCalculator(90);
    static MonitorWaterLineCalculator monitor95 = new MonitorWaterLineCalculator(95);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        Long startTime = System.currentTimeMillis();
        System.out.println("远程调用开始："+startTime);
        Long consume = 0L;
        try {
            result = invoker.invoke(invocation);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Long endTime = System.currentTimeMillis();
            System.out.println("远程调用结束："+endTime);
            consume = endTime - startTime;
            RpcContext.getContext().setAttachment("consume",consume);
            System.out.println("总耗时："+consume+"ms");

            //Long sumRrunTime = Long.valueOf(RpcContext.getContext().getAttachment("sumRrunTime"))+consume;
            //RpcContext.getContext().setAttachment("sumRrunTime",sumRrunTime);
        }

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 1000, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        Long finalConsume = consume;
        executor.execute(() -> {
            while (true) {
                try {
                    //monitor90.accumulate(Double.valueOf(RpcContext.getContext().getAttachment("consume")));
                    monitor90.accumulate(Double.longBitsToDouble(finalConsume));
                    System.out.println("tp90:"+monitor90.getResult());
                    //monitor95.accumulate(Double.valueOf(RpcContext.getContext().getAttachment("consume")));
                    monitor95.accumulate(Double.longBitsToDouble(finalConsume));
                    System.out.println("tp95:"+monitor95.getResult());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return result;
    }
}