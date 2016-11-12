package com.flag.xu.project.system.util;

import com.flag.xu.project.system.annotation.PropertyIgnore;
import com.flag.xu.project.system.config.loader.PropertiesFileConfig;
import com.flag.xu.project.system.pojo.enums.StandardDataType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
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
        return elementsHandle(obj, root);
    }

    private static String getClassName(Element element) {
        StringBuilder sb = new StringBuilder();
        sb.append(PropertiesFileConfig.getProperties().get("pojoPackage"));
        sb.append(".");
        sb.append(element.getName().substring(0, 1).toUpperCase());
        sb.append(element.getName().substring(1));
        return sb.toString();
    }

    private static Object elementsHandle(Object obj, Element element) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Method setMethod;
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(PropertyIgnore.class))
                    continue;
                setMethod = obj.getClass().getMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), field.getType());
                if (setMethod.isAnnotationPresent(PropertyIgnore.class))
                    continue;
                else
                    setMethod.invoke(obj, parameter(field, element));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static Object parameter(Field field, Element foo) {
        Class fieldType = field.getType();
        Object param;
        if (fieldType.equals(StandardDataType.DOUBLE.getType()) || fieldType.equals(StandardDataType.DOUBLE_CLASS.getType())) {
            param = Double.valueOf(foo.elementText(field.getName()) == null ? "0" : foo.elementText(field.getName()));
        } else if (fieldType.equals(StandardDataType.INT.getType()) || fieldType.equals(StandardDataType.INTEGER.getType())) {
            param = Integer.valueOf(foo.elementText(field.getName()) == null ? "0" : foo.elementText(field.getName()));
        } else if (fieldType.equals(StandardDataType.LONG.getType()) || fieldType.equals(StandardDataType.LONG_CLASS.getType())) {
            param = Long.valueOf(foo.elementText(field.getName()) == null ? "0" : foo.elementText(field.getName()));
        } else if (fieldType.equals(StandardDataType.FLOAT.getType()) || fieldType.equals(StandardDataType.FLOAT_CLASS.getType())) {
            param = Float.valueOf(foo.elementText(field.getName()) == null ? "0" : foo.elementText(field.getName()));
        } else if (fieldType.equals(StandardDataType.BOOLEAN.getType()) || fieldType.equals(StandardDataType.BOOLEAN_CLASS.getType())) {
            param = Boolean.valueOf(foo.elementText(field.getName()) == null ? "false" : foo.elementText(field.getName()));
        } else if (fieldType.equals(StandardDataType.SHORT.getType()) || fieldType.equals(StandardDataType.SHORT_CLASS.getType())) {
            param = Short.valueOf(foo.elementText(field.getName()) == null ? "0" : foo.elementText(field.getName()));
        } else if (fieldType.equals(StandardDataType.BYTE.getType()) || fieldType.equals(StandardDataType.BYTE_CLASS.getType())) {
            param = Byte.valueOf(foo.elementText(field.getName()) == null ? "0" : foo.elementText(field.getName()));
        } else {
            param = foo.elementText(field.getName());
        }
        return param;
    }
}
