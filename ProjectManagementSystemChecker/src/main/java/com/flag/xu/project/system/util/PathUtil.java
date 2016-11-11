package com.flag.xu.project.system.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-17:09
 */
public class PathUtil {
    public static <T> Path getPath(Class<T> clazz, String fileName) {
        return getPath(clazz, fileName, null);
    }

    public static <T> Path getPath(Class<T> clazz, String fileName, String suffix) {
        if (StringUtil.isBlank(fileName))
            return null;
        URL url;
        Path path = null;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fileName.startsWith("/") ? fileName : "/" + fileName);

        if (!StringUtil.isBlank(suffix))
            stringBuilder.append(suffix.startsWith(".") ? suffix : "." + suffix);
        try {
            url = clazz.getResource(stringBuilder.toString());
            path = Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return path;
    }

}
