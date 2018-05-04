package zyz.spring.factory;

import zyz.spring.annotations.MyController;
import zyz.spring.annotations.MyRequestMapping;
import zyz.spring.bean.MethodWithController;
import zyz.spring.containers.BeanContainer;
import zyz.spring.containers.RequestMap;

import java.lang.reflect.Method;
import java.util.Map;

public class HandlerMappingFactory {
    public void work() {
        for(Map.Entry<String,Object> entry : BeanContainer.getMap().entrySet()) {
            Class clazz = null;
            try {
                clazz = Class.forName(entry.getKey());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //检查是否为Controller
        if(clazz.isAnnotationPresent(MyController.class)) {
            Method[] methods = clazz.getDeclaredMethods();
            //开始处理controller内部的方法(获取映射)
            MyController annotation = (MyController) clazz.getAnnotation(MyController.class);
            String head = annotation.path();
            for(Method method : methods) {
                if(method.isAnnotationPresent(MyRequestMapping.class)) {
                    MyRequestMapping myRequestMapping = method.getAnnotation(MyRequestMapping.class);
                    MethodWithController mwc = new MethodWithController(method,clazz);
                    RequestMap.getInstance().put(head+myRequestMapping.path(),mwc);
                }
            }
        }
        }
    }
}
