package com.flag.xu.project.system.util;

import com.flag.xu.project.system.config.loader.PropertiesFileConfig;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-10:06
 */
public class XMLUtil {

    public static Object simpleXmlToBean(String input) {
        Document document;
        Object obj = null;
        Element root = null;
        try {
            document = DocumentHelper.parseText(input);
            root = document.getRootElement();
            obj = Class.forName(getClassName(root)).newInstance();
        } catch (ClassNotFoundException e) {
            System.err.println("There are no pojo named \"" + getClassName(root) + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementsHandle(obj, root.elements());
    }

    private static String getClassName(Element element){
        StringBuilder sb = new StringBuilder();
        sb.append(PropertiesFileConfig.getProperties().get("package"));
        sb.append(".");
        sb.append(element.getName().substring(0, 1).toUpperCase());
        sb.append(element.getName().substring(1));
        return sb.toString();
    }

    public static Object elementsHandle(Object obj, List<Element> elements){
        Field[] fields = obj.getClass().getDeclaredFields();
        Method[] methods = obj.getClass().getMethods();
        return null;
    }
}
