package com.flag.xu.project.system;

import com.flag.xu.project.system.annotation.PropertyIgnore;
import com.flag.xu.project.system.pojo.XmlTestPojo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-10:59
 */
public class AnnotationTestMain {
    public static void main(String[] args) {
        Class<XmlTestPojo> testPojoClass = XmlTestPojo.class;
        Field[] fields = testPojoClass.getDeclaredFields();
        Method[] methods = testPojoClass.getDeclaredMethods();
        for (Field field : fields){
            if (field.isAnnotationPresent(PropertyIgnore.class))
                continue;
            else
                System.out.println(field.getName());
        }

        for (Method method : methods){
            if (method.isAnnotationPresent(PropertyIgnore.class))
                continue;
            else
                System.out.println(method.getName());
        }
    }

}
