package com.herman.smart4j.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 *
 * @author hsh
 * @create 2018-05-04 17:13
 **/
public abstract class AspectProxy implements Proxy {
    private static final Logger logger=LoggerFactory.getLogger(AspectProxy.class);


    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result=null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();
        begin();
        return null;
    }

    public void begin() {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {
    }

    public void end() {
    }

}
