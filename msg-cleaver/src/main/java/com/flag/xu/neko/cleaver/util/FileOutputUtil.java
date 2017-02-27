package com.flag.xu.neko.cleaver.util;

import com.flag.xu.project.system.util.PathUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件输出工具类
 *
 * @author xuj
 * @since 2017-02-27 10:31
 */
public class FileOutputUtil {

    /**
     * output string line to file
     *
     * @param content content
     * @param fileName the file's name which will be written
     * @param cover a boolean param, set true will cover old file if the file already exist
     * @throws IOException
     */
    public static void output(String content, String fileName, boolean cover) throws IOException {
        if (fileName == null) {
            return;
        }

        Path path = PathUtil.getPath(FileOutputUtil.class, fileName);
        if (path == null){
            path = Paths.get(PathUtil.getPath(FileOutputUtil.class, ".").toString(), fileName);
            Files.createFile(path);
        } else if (!cover){
            System.out.println("file already exist");
            return;
        }
        List<String> s = new ArrayList<>();
        s.add(content);
        Files.write(path, s, Charset.forName("utf8"), StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("output success the path is " + path.getParent().toString());
    }
}
