package com.herman.smart4j.proxy;

/**
 * 代理接口
 * Created by hsh on 2018/5/4.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
