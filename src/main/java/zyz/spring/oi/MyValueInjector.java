package zyz.spring.oi;

import zyz.spring.annotations.MyValue;
import zyz.spring.properties.MainProperties;

import java.lang.reflect.Field;

public class MyValueInjector implements FieldInjector{
    @Override
    public void inject(Field field, Object o) throws ClassNotFoundException, IllegalAccessException {
        MyValue myValueAnnotation = field.getAnnotation(MyValue.class);
        if(myValueAnnotation==null) {
            return;
        }
        String key = myValueAnnotation.value();
        String value = MainProperties.getInstance().getProperties()
                .getProperty(key);
        if(value == null) {
            return;
        }
        field.setAccessible(true);
        field.set(o,value);
    }
}
