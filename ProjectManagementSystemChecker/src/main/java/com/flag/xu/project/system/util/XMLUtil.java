package com.flag.xu.project.system.util;

import com.flag.xu.project.system.annotation.PropertyIgnore;
import com.flag.xu.project.system.config.loader.PropertiesFileConfig;
import com.flag.xu.project.system.exception.AnnotationConflictException;
import com.flag.xu.project.system.param.cast.ParamCast;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-10:06
 */
public class XMLUtil {

    /**
     * simple xml string deserialize to java object
     *
     * @param input input string of the xml
     * @return java object
     */
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
        return setObjValueSkipIgnore(obj, root);
    }

    /**
     * get the class name by xml root
     *
     * @param element element
     * @return class name
     */
    private static String getClassName(Element element) {
        StringBuilder sb = new StringBuilder();
        sb.append(PropertiesFileConfig.getProperties().get("pojoPackage"));
        sb.append(".");
        sb.append(element.getName().substring(0, 1).toUpperCase());
        sb.append(element.getName().substring(1));
        return sb.toString();
    }

    /**
     * annotation handle,set value to field and skip ignore field and method
     *
     * @param obj     obj
     * @param element element
     * @return object after set value
     */
    private static Object setObjValueSkipIgnore(Object obj, Element element) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Method setMethod;
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(PropertyIgnore.class)) {
                    continue;
                }
                setMethod = obj.getClass().getMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), field.getType());
                if (!setMethod.isAnnotationPresent(PropertyIgnore.class)) {
                    setMethod.invoke(obj, paramHandle(field, element, setMethod));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * handle param
     *
     * @param field  field of class
     * @param foo    the xml element
     * @param method method of class
     * @return param result
     */
    private static Object paramHandle(Field field, Element foo, Method method) {
        Object param = null;
        try {
            param = new ParamCast().paramCast(field, foo, method);
        } catch (AnnotationConflictException e) {
            e.printStackTrace();
        }
        return param;
    }
}
