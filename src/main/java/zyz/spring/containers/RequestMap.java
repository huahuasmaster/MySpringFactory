package zyz.spring.containers;


import zyz.spring.bean.MethodWithController;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * 用于存放访问路径和执行方法之间的映射
 */
public class RequestMap {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private HashMap<String, MethodWithController> map;
    private static volatile RequestMap instance;
    private RequestMap() {
        map = new HashMap<>();
    }
    public static RequestMap getInstance() {
        if(instance == null) {
            synchronized (RequestMap.class){
                if(instance == null) {
                    instance = new RequestMap();
                }
            }
        }
        return instance;
    }
    public MethodWithController get(String path) {
        return map.get(path);
    }
    public void put(String path,MethodWithController method) {
        map.put(path,method);
        logger.info("组成映射："+path+"->"+method.toString());
    }
    public boolean containsKey(String path) {
        return map.containsKey(path);
    }
}
