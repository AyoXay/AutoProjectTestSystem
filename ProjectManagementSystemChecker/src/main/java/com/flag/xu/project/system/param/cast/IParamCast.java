package com.flag.xu.project.system.param.cast;

import org.dom4j.Element;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/13.
 */
public interface IParamCast {
    Object paramCast(Field field, Element foo);
}
