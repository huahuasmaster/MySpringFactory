package zyz.spring.factory;

import zyz.spring.annotations.*;
import zyz.spring.containers.BeanContainer;
import zyz.spring.oi.FieldInjector;
import zyz.spring.oi.FieldInjectorMap;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyBeanFactory {
    //存储需要扫描的类的全限定名
    ArrayList<String> classNames = new ArrayList<>();

    public void work(String scanPath) throws FileNotFoundException {
        getClassNames(scanPath);
        scanAndInit();
        scanAndWire();
    }

    private void scanAndWire() {
        for(Map.Entry<String,Object> entry : BeanContainer.getMap().entrySet()) {
            try {
                //需要执行注入的对象
                Class clazz  = Class.forName(entry.getKey());
                Object o = entry.getValue();
                //遍历clazz内的所有字段，看看有没有要赋值的
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields) {
                    field.setAccessible(true);
                    searchInFieldInjectorMap(field,o);
                }
            } catch (ClassNotFoundException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查此字段是否需要注入，具体操作为查看字段的注解是否在FileInjector中注册
     * 如果有注册，就
     * @param field
     * @param o
     */
    private void searchInFieldInjectorMap(Field field, Object o) throws IllegalAccessException, ClassNotFoundException {
        HashMap<Class,FieldInjector> map = FieldInjectorMap.getInstance().getMap();
        for(Map.Entry<Class,FieldInjector> entry : map.entrySet()) {
            if(field.isAnnotationPresent(entry.getKey())) {
                entry.getValue().inject(field,o);
            }
        }
    }

    /**
     * 将对象初始化并填入容器
     *
     */
    private void scanAndInit() {
        for (String className : classNames) {

            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
            //检查是否为需要放入容器中的对象
            if (clazz.isAnnotationPresent(MyService.class) ||
                    clazz.isAnnotationPresent(MyController.class)) {
                Constructor constructor = null;//用于创建示例
                Object instance = null;
                Method[] methods = null;

                //创建示例
                try {
                    //默认使用无参构造器
                    constructor = clazz.getConstructor(null);
                    methods = clazz.getDeclaredMethods();
                    constructor.setAccessible(true);
                    instance = constructor.newInstance(null);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }

                //寻找是否有注册init的方法，找到并执行
                for (Method method : methods) {
                    if (method.isAnnotationPresent(MyInitMethod.class)) {
                        try {
                            method.setAccessible(true);
                            method.invoke(instance, (Object[]) null);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }

                //将对象填入容器
                BeanContainer.getMap().put(clazz.getName(), instance);
            }

        }
    }

    /**
     * 扫描配置文件所指明的包
     *
     * @param packageName 形如com.zust.demo的包名
     */
    private void getClassNames(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File file = new File(url.getFile());
        for (File mFile : file.listFiles()
                ) {
            if (mFile.isDirectory()) {
                getClassNames(packageName + "." + mFile.getName());
            } else {
                classNames.add(packageName + "." + mFile.getName().replace(".class", ""));
            }
        }
    }
}
