package com.flag.xu.transport;

import com.flag.xu.neko.core.utils.PathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * transport module main class
 *
 * @author xuj
 * @since 2017-03-08-15:16
 */
public class TransportationCenter {

    private static final Logger log = LogManager.getLogger(TransportationCenter.class);

    private static final Properties properties = new Properties();
    private static String host;
    private static int port;

    public static void main(String[] args) {
        init();
        new Transportation(host, port).connect();
    }

    private static void init() {
        Path path = PathUtil.getPath(TransportationCenter.class, "host.properties");
        try (InputStream is = Files.newInputStream(path)) {
            properties.load(is);
        } catch (Exception e) {
            log.error("load properties failed, cause by {}", e.getMessage());
        }

        host = properties.getProperty("host");
        String portStr = properties.getProperty("port");
        port = Integer.valueOf(StringUtils.isNumeric(portStr) ? portStr : "0");
    }
}
