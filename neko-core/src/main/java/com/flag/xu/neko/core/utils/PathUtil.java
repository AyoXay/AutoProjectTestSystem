package com.flag.xu.neko.core.utils;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-17:09
 */
public class PathUtil {
    /**
     * get the {@link Path} accord to class file, this method will find the classes root path
     *
     * @param clazz    class
     * @param fileName the fileName you want find
     * @param <T>      generic type
     * @return the path, will be null if the path cannot find or file name is null
     */
    public static <T> Path getPath(Class<T> clazz, String fileName) {
        return getPath(clazz, fileName, null);
    }

    /**
     * get the {@link Path} accord to class file, this method will find the classes root path
     *
     * @param clazz    class
     * @param fileName the fileName you want find
     * @param suffix   the file's suffix
     * @param <T>      generic type
     * @return the path, will be null if the path cannot find or file name is null
     */
    public static <T> Path getPath(Class<T> clazz, String fileName, String suffix) {
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }

        Path path = null;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fileName.startsWith("/") ? fileName : "/" + fileName);

        if (StringUtils.isNotEmpty(suffix)) {
            String tmp = suffix.startsWith(".") ? suffix : "." + suffix;
            if (!fileName.endsWith(tmp)) {
                stringBuilder.append(tmp);
            }
        }

        try {
            URL url = clazz.getResource(stringBuilder.toString());
            if (url != null) {
                path = Paths.get(url.toURI());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * get the {@link Path}
     *
     * @param path     path string
     * @param fileName file name
     * @return the path object
     */
    @Nullable
    public static Path getPath(@NotNull String path, String fileName) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }

        Path result;
        if (fileName != null) {
            result = Paths.get(path, fileName);
        } else {
            result = Paths.get(path);
        }
        return result;
    }

}
