package com.lagou.proxy;

public class TransactionProxyFactory {
    private static TransactionProxyFactory transactionProxyFactory;
    private TransactionProxyFactory(){}
    public static TransactionProxyFactory init (){
        if(transactionProxyFactory == null){
            transactionProxyFactory = new TransactionProxyFactory();
        }
        return transactionProxyFactory;
    }
    public TransactionJdkProxy getTransactionProxy(){
        TransactionJdkProxy transactionJdkProxy = TransactionJdkProxy.init();
        return transactionJdkProxy;
    }
}
