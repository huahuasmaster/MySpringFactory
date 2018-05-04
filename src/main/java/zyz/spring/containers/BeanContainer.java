package zyz.spring.containers;

import zyz.spring.annotations.MyController;
import zyz.spring.annotations.MyRequestMapping;
import zyz.spring.bean.MethodWithController;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 存放Bean（单例）的容器
 */
public class BeanContainer {
    private static volatile HashMap<String,Object> map;
    public static HashMap<String, Object> getMap() {
        if(map == null) {
            synchronized (BeanContainer.class) {
                if(map == null) {
                    map = new HashMap<>();
                }
            }
        }
        return map;
    }


    /**
     * 将bean初始化后放入容器的操作，对于特殊的bean会做额外操作，比如bean
     * @param name 类的全限定名称
     * @return 初始化后的示例
     */
//    private Object initBean(String name) {
//        Class clazz = null;
//        Object instance = null;
//        try {
//           clazz = Class.forName(name);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        //检查是否为Controller
//        if(clazz.isAnnotationPresent(MyController.class)) {
//            Method[] methods = clazz.getDeclaredMethods();
//
//            //先将其初始化、放入bean容器
//            try {
//                instance = createBean(clazz,methods);
//            } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            //开始处理controller内部的方法(获取映射)
//            MyController annotation = (MyController) clazz.getAnnotation(MyController.class);
//            String head = annotation.path();
//            for(Method method : methods) {
//                if(method.isAnnotationPresent(MyRequestMapping.class)) {
//                    MyRequestMapping myRequestMapping = method.getAnnotation(MyRequestMapping.class);
//                    MethodWithController mwc = new MethodWithController(method,clazz);
//                    RequestMap.getInstance().put(head+myRequestMapping.path(),mwc);
//                }
//            }
//        }
//        if(instance != null) {
//            map.put(name,instance);
//        }
//        return instance;
//    }


}
