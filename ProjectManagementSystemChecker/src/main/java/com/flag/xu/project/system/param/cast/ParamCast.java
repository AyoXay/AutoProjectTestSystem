package com.flag.xu.project.system.param.cast;

import org.dom4j.Element;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/13.
 */
public class ParamCast implements IParamCast {
    @Override
    public Object paramCast(Field field, Element foo) {
        return new IntegerCast().paramCast(field, foo);
    }
}
