package com.herman.smart4j.aspect;

import com.herman.smart4j.annotation.Aspect;
import com.herman.smart4j.annotation.Controller;
import com.herman.smart4j.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 拦截Ctroller所有方法
 *
 * @author hsh
 * @create 2018-05-07 9:09
 **/
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        logger.debug("========begin====");
        logger.debug(String.format("class: %s", cls.getName()));
        logger.debug(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        logger.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
        logger.debug("======end======");
    }
}
