package com.flag.cu.transport.util;

/**
 * bytes convert util
 *
 * @author xuj
 * @version 1.0-SNAPSHOT
 * @since 2017-03-09-13:41
 */
public class BytesUtil {

    /**
     * str to bytes convert some hex string like "ff fe fc 22 22 03 03"
     * @param string string
     * @param regex regex
     * @param offset offset
     * @param length length
     * @return byte array
     */
    public static byte[] str2Bytes(String string, String regex, int offset, int length){
        String[] strings = string.split(regex);
        if (strings.length < offset + length){
            throw new ArrayIndexOutOfBoundsException();
        }
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++){
            bytes[i] = (byte) Integer.parseInt(strings[offset + i], 16);
        }
        return bytes;
    }
}
