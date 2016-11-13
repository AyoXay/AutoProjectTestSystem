package com.flag.xu.project.system.param.cast;

import com.flag.xu.project.system.pojo.enums.StandardDataType;
import org.dom4j.Element;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/13.
 */
public class ByteCast extends ParamCast {
    @Override
    public Object paramCast(Field field, Element foo) {
        Class fieldType = field.getType();
        if (fieldType.equals(StandardDataType.BYTE.getType()) || fieldType.equals(StandardDataType.BYTE_CLASS.getType())) {
            Byte param = Byte.valueOf(foo.elementText(field.getName()) == null ? "0" : foo.elementText(field.getName()));
            return param;
        } else {
            return new CharacterCast().paramCast(field, foo);
        }
    }
}
