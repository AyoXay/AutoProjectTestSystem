package com.flag.cu.transport.util;

import org.junit.Test;

/**
 * @author xuj
 * @since 2017-03-09-14:25
 */
public class BytesUtilTest {
    @Test
    public void test_str2Bytes(){
        String s = "ff fe fc 01 12 79 88";
        byte[] bytes = BytesUtil.str2Bytes(s, " ", 0, 7);
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(bytes[i]);
        }
    }

    @Test
    public void test(){
        String s = "ff fe fc 01 12 79 88";
        String[] strings = s.split(" ");
        for (int i = 0; i < strings.length; i++) {
            System.out.println(Integer.parseInt(strings[i], 16));
        }
    }
}
