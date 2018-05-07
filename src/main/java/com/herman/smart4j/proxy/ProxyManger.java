package com.herman.smart4j.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 *
 *
 * CGLib创建代理步骤最终结果生产一个代理(Enhancer.create)代理需要：
 * 1.实现MethodInterceptor接口
 * 2.重写intercept方法
 * 3.MethodProxy.invokeSuper(目标,方法参数)
 *
 * @author hsh
 * @create 2018-05-04 16:55
 **/
public class ProxyManger {


    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            public Object intercept(Object targetObject, Method targetMethod, Object[] args, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, args, proxyList).doProxyChain();
            }
        });
    }
}
