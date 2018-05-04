package zyz.spring.annotations;

import java.lang.annotation.*;

//用来定义注解的注解被称为元注解
@Documented//表示是否将java注解信息添加到java文档中
@Retention(RetentionPolicy.RUNTIME)//指明注解的控制域，SOURCE表示注解只存在于源码中，在字节码中不存在；
// Class表示会在字节码中存在，但运行时无法获得；Runtime：可以在运行时通过反射获取.
@Target(ElementType.TYPE)//指明注解所作用的目标，TYPE用于修饰类、接口或enum声明；METHOD用于描述方法；以此类推
@Inherited//如果此注解（MyController）用于修饰了某个类，那么其子类也会被注解
public @interface MyController {
    String path() default "/test";//这一条语句是“注解方法”，用于指明在使用注解时需要填入的参数。
    //注解方法不能带参数，返回类型限制为基本类型、String、Enum、Annotation以及上述类型的数组
    //注解方法可以用默认值
}
