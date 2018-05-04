package zyz.spring.bean;


import java.lang.reflect.Method;

public class MethodWithController {
    private Method method;
    private Class controllerClass;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
    }

    @Override
    public String toString() {
        return "MethodWithController{" +
                "method=" + method +
                ", controllerClass=" + controllerClass +
                '}';
    }

    public MethodWithController() {
    }

    public MethodWithController(Method method, Class controllerClass) {
        this.method = method;
        this.controllerClass = controllerClass;
    }
}
