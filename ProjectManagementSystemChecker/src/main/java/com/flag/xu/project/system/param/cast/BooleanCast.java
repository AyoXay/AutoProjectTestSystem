package com.flag.xu.project.system.param.cast;

import com.flag.xu.project.system.pojo.enums.StandardDataType;
import org.dom4j.Element;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/13.
 */
public class BooleanCast extends ParamCast {
    @Override
    public Object paramCast(Field field, Element foo) {
        Class fieldType = field.getType();
        if (fieldType.equals(StandardDataType.BOOLEAN.getType()) || fieldType.equals(StandardDataType.BOOLEAN_CLASS.getType())) {
            Boolean param = Boolean.valueOf(foo.elementText(field.getName()) == null ? "false" : foo.elementText(field.getName()));
            return param;
        } else {
            return new LongCast().paramCast(field, foo);
        }
    }
}
