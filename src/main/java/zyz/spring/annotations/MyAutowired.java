package zyz.spring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)//字段的注入
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAutowired {
    String name() default "";
}
