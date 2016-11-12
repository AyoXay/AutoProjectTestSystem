package com.flag.xu.project.system.pojo.enums;

/**
 * Created by Administrator on 2016/11/11.
 */
public enum StandardDataType {
    INT(int.class),
    INTEGER(Integer.TYPE),
    LONG(long.class),
    LONG_CLASS(Long.TYPE),
    DOUBLE(double.class),
    DOUBLE_CLASS(Double.TYPE),
    FLOAT(float.class),
    FLOAT_CLASS(Float.TYPE),
    BOOLEAN(boolean.class),
    BOOLEAN_CLASS(Boolean.TYPE),
    BYTE(byte.class),
    BYTE_CLASS(Byte.TYPE),
    CHAR(char.class),
    CHARACTER(Character.TYPE),
    SHORT(short.class),
    SHORT_CLASS(Short.TYPE);

    private Class type;

    StandardDataType(Class type){
        this.type = type;
    }

    public Class getType() {
        return type;
    }
}
