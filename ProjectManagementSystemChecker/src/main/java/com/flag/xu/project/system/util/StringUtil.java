package com.flag.xu.project.system.util;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-17:14
 */
public class StringUtil {
    public static boolean isBlank(String s){
        boolean b = true;
        if (s != null && !"".equals(s) && s.length() > 0)
            b = false;
        return b;
    }
}
