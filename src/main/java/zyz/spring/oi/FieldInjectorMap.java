package zyz.spring.oi;

import zyz.spring.annotations.MyAutowired;
import zyz.spring.annotations.MyValue;

import java.util.HashMap;

public class FieldInjectorMap {
    private static volatile FieldInjectorMap instance;
    private HashMap<Class,FieldInjector> injectorMap;
    public static FieldInjectorMap getInstance() {
        if (instance == null) {
            synchronized (FieldInjectorMap.class) {
                if(instance == null) {
                    instance = new FieldInjectorMap();
                }
            }
        }
        return instance;
    }
    private FieldInjectorMap() {
        injectorMap = new HashMap<>();
        //将所有的注入器放入map
        injectorMap.put(MyAutowired.class,new MyAutowiredInjector());
        injectorMap.put(MyValue.class,new MyValueInjector());
    }
    public HashMap<Class,FieldInjector> getMap() {
        return injectorMap;
    }

}
