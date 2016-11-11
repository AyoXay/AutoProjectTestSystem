package com.flag.xu.project.system.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-10:06
 */
public class XMLUtil {
    public static <T> T xmlToBean(String input) throws DocumentException {
        Document document = null;
        List<T> list = new ArrayList<>();
        document = DocumentHelper.parseText(input);
        Element root = document.getRootElement();
        return null;
    }
}
