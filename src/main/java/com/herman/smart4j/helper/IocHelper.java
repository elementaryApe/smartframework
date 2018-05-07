package com.herman.smart4j.helper;

import com.herman.smart4j.annotation.Inject;
import com.herman.smart4j.utils.CollectionUtil;
import com.herman.smart4j.utils.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入工具类
 *
 * @author hsh
 * @create 2018-04-28 16:49
 **/
public class IocHelper {

    static {
        //获取所有的Bean类与Bean实例之间映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Object beanInstance = beanEntry.getValue();
                Class<?> beanClass = beanEntry.getKey();
                //获取所有的成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field field : beanFields) {
                        //判断当前成员变量是否带有Inject注解
                        if (field.isAnnotationPresent(Inject.class)) {
                            //在BeanMap中取得对应的Field实例
                            Class<?> fieldClass = field.getType();
                            Object beanFieldInstance = beanMap.get(fieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化BeanField
                                ReflectionUtil.setField(beanInstance,field,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }

}
