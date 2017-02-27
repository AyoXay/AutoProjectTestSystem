package com.flag.xu.neko.cleaver.amput;

import com.sun.istack.internal.NotNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * This a default message disconnection class
 *
 * @author xuj
 * @version 1.0-SNAPSHOT
 * @since 2017-02-24 17:12
 */
public class DefaultMessageDiacon<T extends Path> extends AbstractDiscon<T> {

    @Override
    public String unpack(@NotNull String input, int offset) {
        StringBuilder result = new StringBuilder(input.length());
        if (input.contains("\n") || input.contains("\r")) {
            List<String> inputs = Arrays.asList(input.split(System.getProperty("line.separator")));
            inputs.forEach(s -> result.append(s.substring(offset)));
        } else {
            result.append(input.substring(offset));
        }
        return result.toString();
    }
}
