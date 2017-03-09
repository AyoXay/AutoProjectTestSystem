package com.flag.xu.neko.cleaver.amput;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * abstract message unpack or disconnection class
 *
 * @author xuj
 * @version 1.0-SNAPSHOT
 * @since 2017-02-24-16:57
 */
public abstract class AbstractDiscon<T extends Path> {

    /**
     * 切割消息字符串，取出无用字符，取出主消息体，主要用于日志内报文切割
     * 覆盖此方法自定义切割实现
     *
     * @param input  输入字符串
     * @param offset 偏移量，多行默认每行偏移offset长的字符
     * @return 切割后的字符串
     */
    public abstract String unpack(String input, int offset);

    /**
     * 切割消息文件，取出无用字符，取出主消息体，主要用于日志内报文切割
     * 覆盖此方法自定义切割实现
     *
     * @param path   输入文件
     * @param offset 偏移量，多行默认每行偏移offset长的字符
     * @return 切割后的字符串
     */
    public String unpack(T path, int offset) {
        if (path == null) {
            throw new NullPointerException("path is null");
        }
        StringBuilder result = new StringBuilder();
        List<String> lines = unpack0(path, offset);
        lines.forEach(s -> {
            if (StringUtils.isNotEmpty(s)) {
                result.append(s);
            }
            result.append(System.getProperty("line.separator"));
        });
        return result.toString();
    }

    /**
     * 切割消息文件，去除无用字符，取出主消息体
     * 以空行作为分隔符，存放在一个字符串list中
     *
     * @param path 文件path
     * @param offset 偏移量
     * @return 切割后字符串list
     */
    public List<String> unpack0(T path, int offset) {
        if (path == null) {
            throw new NullPointerException("path is null");
        }
        List<String> resList = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(s -> {
                if (StringUtils.isEmpty(s)) {
                    resList.add(tmp.toString());
                    tmp.delete(0, tmp.length());
                }
                if (StringUtils.isNotEmpty(s) && !s.startsWith("/")) {
                    msgNormalize(tmp, s.substring(offset));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resList;
    }

    /**
     * 生成16进制报文
     *
     * @param builder 处理结果对象
     * @param line    原始字符串
     */
    private void msgNormalize(StringBuilder builder, String line) {
        for (int i = 0; i < line.length() - 1; i += 2) {
            if (line.charAt(i) == '-') {
                i++;
            }
            builder.append(line.charAt(i));
            builder.append(line.charAt(i + 1));
            builder.append(" ");
        }
    }
}
