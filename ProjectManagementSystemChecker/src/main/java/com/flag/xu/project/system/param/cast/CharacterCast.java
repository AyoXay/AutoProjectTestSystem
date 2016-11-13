package com.flag.xu.project.system.param.cast;

import com.flag.xu.project.system.pojo.enums.StandardDataType;
import org.dom4j.Element;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/13.
 */
public class CharacterCast extends ParamCast {
    @Override
    public Object paramCast(Field field, Element foo) {
        Class fieldType = field.getType();
        if (fieldType.equals(StandardDataType.CHAR.getType()) || fieldType.equals(StandardDataType.CHARACTER.getType())) {
            Character param = (foo.elementText(field.getName()) == null ? "" : foo.elementText(field.getName())).charAt(0);
            return param;
        } else {
            return foo.elementText(field.getName());
        }
    }
}
