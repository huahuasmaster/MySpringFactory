package zyz.spring.oi;

import zyz.spring.containers.BeanContainer;

import java.lang.reflect.Field;
import java.util.Map;

public class MyAutowiredInjector implements FieldInjector {
    @Override
    public void inject(Field field, Object o) throws ClassNotFoundException, IllegalAccessException {
        //判断需要赋值的是不是一个接口
        Class mType = field.getType();
        if (mType.isInterface()) {
            //在容器中寻找实现类
            for (Map.Entry<String, Object> mEntry :
                    BeanContainer.getMap().entrySet()) {
                Class mClass = Class.forName(mEntry.getKey());
                for (Class in : mClass.getInterfaces()) {
                    if (mType == in) {
                        field.set(o, mEntry.getValue());
                        return;
                    }
                }
            }
        } else {
            //不是接口的话直接注入
            String fullName = mType.getName();
            field.set(o, BeanContainer.getMap().get(fullName));
        }
    }
}
