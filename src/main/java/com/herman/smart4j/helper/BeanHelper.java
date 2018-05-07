package com.herman.smart4j.helper;

import com.herman.smart4j.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean辅助工具类
 *
 * @author hsh
 * @create 2018-04-28 16:28
 **/
public class BeanHelper {

    /**
     * 定义Bean 映射(用于存放Bean类与Bean实例的映射关系)
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取Bean 映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean 实例
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("不能获取bean 通过Class:" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 设置Bean实例
     */
    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }


}
