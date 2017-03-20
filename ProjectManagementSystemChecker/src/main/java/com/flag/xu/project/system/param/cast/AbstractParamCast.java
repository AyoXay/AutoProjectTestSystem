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

    /**
     * cast param
     *
     * @param field  field
     * @param foo    xml element
     * @param method methos
     * @return param obj
     * @throws AnnotationConflictException
     */
    public abstract Object paramCast(Field field, Element foo, Method method) throws AnnotationConflictException;

    /**
     * get the param name of the class, if annotation by property class, use the property value as param's name
     *
     * @param field  field
     * @param method method
     * @return param's name
     * @throws AnnotationConflictException
     */
    protected String getParamName(Field field, Method method) throws AnnotationConflictException {
        boolean fieldIsAnnotation = field.isAnnotationPresent(Property.class);
        boolean methodIsAnnotation = method.isAnnotationPresent(Property.class);
        String fieldName = fieldIsAnnotation ? field.getAnnotation(Property.class).value() : field.getName();
        String methodName = methodIsAnnotation ? method.getAnnotation(Property.class).value() : method.getName();
        if (fieldIsAnnotation && methodIsAnnotation && !fieldName.equals(methodName)) {
            throw new AnnotationConflictException();
        }
        return fieldName;

    }
}
