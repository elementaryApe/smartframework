package com.herman.smart4j.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * ElementType.TYPE 基于类的注解
 * Created by hsh on 2018/5/4.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     *  返回Annotation 类型的泛型
     */
    Class<? extends Annotation> value();
}
