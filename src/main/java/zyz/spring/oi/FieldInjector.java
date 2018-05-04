package zyz.spring.oi;

import java.lang.reflect.Field;

public interface FieldInjector {
    /**
     *
     * @param field 需要执行注入的字段
     * @param o 对象实例
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    void inject(Field field,Object o) throws ClassNotFoundException,IllegalAccessException;
}
