package zyz.spring;

import zyz.spring.bean.MethodWithController;
import zyz.spring.factory.HandlerMappingFactory;
import zyz.spring.factory.MyBeanFactory;
import zyz.spring.containers.BeanContainer;
import zyz.spring.containers.RequestMap;
import zyz.spring.properties.MainProperties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Logger;


public class MainServlet extends HttpServlet {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
//        System.out.println(url);
        //处理url
        String context = req.getContextPath();
//        logger.info("context:"+req.getServletPath());
        url = url.replace(context,"").replaceAll("/+","/");
        logger.info("有外界访问:"+url);
        if(!RequestMap.getInstance().containsKey(url)) {
//            resp.setStatus(404);
            resp.getWriter().print("404 not found");
            return;
        }
        MethodWithController mwc = RequestMap.getInstance().get(url);
        Class clazz = mwc.getControllerClass();
        Object o = BeanContainer.getMap().get(clazz.getName());
        try {
            //todo 注入参数
            String result = (String) mwc.getMethod().invoke(o, (Object[]) null);
            resp.getWriter().print(result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        try {
            doLoadConfig(config);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //2.扫描相关的类、执行依赖注入 (Spring IOC)
        doScan();


        //3.初始化HandlerMapping  (Spring MVC)
        doHandlerMapping();
    }

    private void doHandlerMapping() {
        new HandlerMappingFactory().work();
    }


    private void doScan() {
        try {
            new MyBeanFactory().work(
                    MainProperties.getInstance().getProperties().getProperty("scannerPackage"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doLoadConfig(ServletConfig config) throws IOException {
        MainProperties.getInstance();
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

    }

}
