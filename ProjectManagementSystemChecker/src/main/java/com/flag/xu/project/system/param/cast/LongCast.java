package com.flag.xu.project.system.param.cast;

import com.flag.xu.project.system.pojo.enums.StandardDataType;
import org.dom4j.Element;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/13.
 */
public class LongCast extends ParamCast {
    @Override
    public Object paramCast(Field field, Element foo) {
        Class fieldType = field.getType();
        if (fieldType.equals(StandardDataType.LONG.getType()) || fieldType.equals(StandardDataType.LONG_CLASS.getType())) {
            Long param = Long.valueOf(foo.elementText(field.getName()) == null ? "0" : foo.elementText(field.getName()));
            return param;
        } else {
            return new FloatCast().paramCast(field, foo);
        }
    }
}
