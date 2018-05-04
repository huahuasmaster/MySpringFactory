package zyz.spring.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 读取并保存配置文件的信息
 */
public class MainProperties {
    private static final String FILE_NAME = "application.properties";
    Logger logger = Logger.getLogger(this.getClass().getName());
    private Properties properties;
    private static volatile MainProperties instance;
    private MainProperties() throws IOException {
        properties = new Properties();
        properties.load(
                this.getClass().getClassLoader().getResourceAsStream(
                        FILE_NAME
                ));
        logger.info("配置文件加载完毕");
    }

    public static MainProperties getInstance()  {
        if (instance == null) {
            synchronized (MainProperties.class) {
                if(instance == null) {
                    try {
                        instance = new MainProperties();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public Properties getProperties() {
        return properties;
    }
}
