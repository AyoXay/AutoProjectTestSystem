package com.flag.xu.project.system.config.loader;

import com.flag.xu.project.system.util.PathUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-14:18
 */
public class PropertiesFileConfig {

    private static volatile Properties properties;
    private static volatile boolean isInit = false;
    private static Path path;

    public static Properties getProperties() {
        if (properties != null)
            return properties;
        else {
            startThread();
            while(!isInit)
                continue;
        }
        return properties;
    }

    private static void startThread() {
        properties = new Properties();

        path = PathUtil.getPath(PropertiesFileConfig.class, "system.properties");

        new Thread(() -> {
            while (true) {
                try {
                    properties.load(Files.newInputStream(path));
                    isInit = true;
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
