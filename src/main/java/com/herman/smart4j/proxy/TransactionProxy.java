package com.herman.smart4j.proxy;

import com.herman.smart4j.annotation.Transaction;
import com.herman.smart4j.helper.DataBaseHepler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 事物代理
 *
 * @author hsh
 * @create 2018-05-07 12:00
 **/
public class TransactionProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

    //同一线程中事物控制逻辑只执行一次
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        try {
            Boolean flag = FLAG_HOLDER.get();
            Method targetMethod = proxyChain.getTargetMethod();
            if (!flag && targetMethod.isAnnotationPresent(Transaction.class)) {
                FLAG_HOLDER.set(true);
                DataBaseHepler.beginTransaction();
                logger.debug("begin transaction");
                result = proxyChain.doProxyChain();
                DataBaseHepler.commitTransaction();
                logger.debug("commit transaction");
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            DataBaseHepler.rollbackTransaction();
            logger.debug("rollback transaction");
            throw e;
        } finally {
            FLAG_HOLDER.remove();
        }
        return result;
    }
}
