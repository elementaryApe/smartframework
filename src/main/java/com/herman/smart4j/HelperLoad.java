package com.herman.smart4j;

import com.herman.smart4j.helper.*;
import com.herman.smart4j.utils.ClassUtil;

/**
 * 加载相应Helper工具
 *
 * @author hsh
 * @create 2018-04-28 17:50
 **/
public class HelperLoad {

    public static void init() {
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, ConfigHelper.class, ControllerHelper.class, AopHelper.class, IocHelper.class};
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
