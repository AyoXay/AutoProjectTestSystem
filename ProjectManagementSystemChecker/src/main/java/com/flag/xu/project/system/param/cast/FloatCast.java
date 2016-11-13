package com.flag.xu.project.system.param.cast;

import com.flag.xu.project.system.pojo.enums.StandardDataType;
import org.dom4j.Element;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/13.
 */
public class FloatCast extends ParamCast {
    @Override
    public Object paramCast(Field field, Element foo) {
        Class fieldType = field.getType();
        if (fieldType.equals(StandardDataType.FLOAT.getType()) || fieldType.equals(StandardDataType.FLOAT_CLASS.getType())) {
            Float param = Float.valueOf(foo.elementText(field.getName()) == null ? "0" : foo.elementText(field.getName()));
            return param;
        } else {
            return new ShortCast().paramCast(field, foo);
        }
    }
}
