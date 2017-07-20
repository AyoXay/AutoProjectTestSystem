package com.flag.xu.project.system.param.cast;

import com.flag.xu.project.system.exception.AnnotationConflictException;
import com.flag.xu.project.system.pojo.enums.StandardDataType;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/13.
 */
public class BooleanCast extends ParamCast {
    @Override
    public Object paramCast(Field field, Element foo, Method method) throws AnnotationConflictException {
        Class fieldType = field.getType();
        if (fieldType.equals(StandardDataType.BOOLEAN.getType()) || fieldType.equals(StandardDataType.BOOLEAN_CLASS.getType())) {
            return Boolean.valueOf(foo.elementText(getParamName(field, method)) == null ? "false" : foo.elementText(getParamName(field, method)));
        } else {
            return new LongCast().paramCast(field, foo, method);
        }
    }
}
