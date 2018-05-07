package com.herman.smart4j.helper;

import com.herman.smart4j.annotation.Action;
import com.herman.smart4j.bean.Handler;
import com.herman.smart4j.bean.Request;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Controller辅助工具类
 *
 * @author hsh
 * @create 2018-04-28 17:25
 **/
public final class ControllerHelper {

    /**
     * 封装请求与处理器的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        //获取所有的controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                //获取方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    //判断是否带有Action 注解的方法
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            //获取URL映射规则并验证
                            Action annotation = method.getAnnotation(Action.class);
                            String mapping = annotation.value();
                            if (mapping.matches("\\w+:/\\w*")) {//任意字符 或者任意字符开头
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    //获取请求方法与请求路径(可以标准化出来Request.Method)
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
