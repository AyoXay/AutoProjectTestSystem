package com.flag.xu.neko.cleaver.amput;

/**
 *  abstract message unpack or disconnection class
 * @author xuj
 * @since  2017-02-24-16:57
 * @version 1.0-SNAPSHOT
 */
public abstract class AbstractDiscon<T> {

    /**
     * 切割消息字符串，取出无用字符，取出主消息体，主要用于日志内报文切割
     * 覆盖此方法自定义切割实现
     *
     * @param input 输入字符串
     * @param offset 偏移量，多行默认每行偏移offset长的字符
     * @return 切割后的字符串
     */
    public abstract String unpack(String input, int offset);

    public String unpack(T obj, int... offsets){
        StringBuilder result = new StringBuilder();
        return result.toString();
    }
}
