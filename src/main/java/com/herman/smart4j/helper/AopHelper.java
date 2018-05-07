package com.herman.smart4j.helper;

import com.herman.smart4j.annotation.Aspect;
import com.herman.smart4j.annotation.Service;
import com.herman.smart4j.proxy.AspectProxy;
import com.herman.smart4j.proxy.Proxy;
import com.herman.smart4j.proxy.ProxyManger;
import com.herman.smart4j.proxy.TransationProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截辅助器
 *
 * @author hsh
 * @create 2018-05-07 9:48
 **/
public final class AopHelper {

    private static final Logger logger = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManger.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            logger.error("aop 错误:", e);
        }
    }

    /**
     * 获取带有Aspect 所有注解的类
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if (!annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 获取代理类及其目标类集合
     * 扩展了AspectProxy切面类且带有Aspect注解的类才能根据Aspect注解中所定义的注解属性去获取该注解对应的目标类集合
     * 然后才能建立代理类与目标类集合直接的映射关系
     * 最终返回这个映射关系
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        //获取所有代理类(切面类)
        addAspectProxy(proxyMap);
        //获取事物代理类
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 获取代理类及其目标类集合
     * 扩展了AspectProxy切面类且带有Aspect注解的类才能根据Aspect注解中所定义的注解属性去获取该注解对应的目标类集合
     * 然后才能建立代理类与目标类集合直接的映射关系
     * 最终返回这个映射关系
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    /**
     * 事物代理
     */
    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransationProxy.class, serviceClassSet);
    }

    /**
     * 获取目标类与代理对象列表直接映射关系
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxies = new ArrayList<Proxy>();
                    proxies.add(proxy);
                    targetMap.put(targetClass, proxies);
                }
            }
        }
        return targetMap;
    }


}
