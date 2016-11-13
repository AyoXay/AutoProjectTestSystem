package com.flag.xu.project.system.param.cast;

import com.flag.xu.project.system.annotation.Property;
import com.flag.xu.project.system.exception.AnnotationConflictException;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/13.
 */
public abstract class AbstractParamCast {
    public abstract Object paramCast(Field field, Element foo, Method method) throws AnnotationConflictException;

    protected String getParamName(Field field, Method method) throws AnnotationConflictException {
        boolean fieldIsAnnotation = field.isAnnotationPresent(Property.class);
        boolean methodIsAnnotation = method.isAnnotationPresent(Property.class);
        String fieldName = fieldIsAnnotation ? field.getAnnotation(Property.class).value() : field.getName();
        String methodName = methodIsAnnotation ? method.getAnnotation(Property.class).value() : method.getName();
        if (fieldIsAnnotation && methodIsAnnotation && !fieldName.equals(methodName))
            throw new AnnotationConflictException();
        return fieldName;

    }
}
