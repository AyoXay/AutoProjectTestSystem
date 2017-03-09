package com.flag.xu.neko.core.utils;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * 文件输出工具类
 *
 * @author xuj
 * @since 2017-02-27 10:31
 */
public class FileOutputUtil {

    /**
     * output string lines to file
     *
     * @param content  content
     * @param fileName the file's name which will be written
     * @param cover    a boolean param, set true will cover old file if the file already exist
     * @throws IOException
     */
    public static void output(@NotNull List<String> content, @NotNull String fileName, boolean cover) throws IOException {
        if (fileName == null || content == null) {
            throw new NullPointerException("content or fileName is null, please check it");
        }

        Path path = PathUtil.getPath(FileOutputUtil.class, fileName);
        if (path == null) {
            path = Paths.get(PathUtil.getPath(FileOutputUtil.class, ".").toString(), fileName);
            Files.createFile(path);
        } else if (!cover) {
            System.out.println("file already exist");
            return;
        }

        Files.write(path, content, Charset.forName("utf8"), StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("output success the path is " + path.getParent().toString());
    }

    /**
     * output string line to file
     *
     * @param content  string
     * @param fileName file's name
     * @param cover    cover or not, will cover the old file if true and file already exist
     * @throws IOException
     */
    public static void output(@NotNull String content, @NotNull String fileName, boolean cover) throws IOException {
        if (fileName == null || content == null) {
            throw new NullPointerException("fileName or content is null, please check it");
        }
        List<String> write = Arrays.asList(content.split(System.getProperty("line.separator")));
        output(write, fileName, cover);
    }
}
