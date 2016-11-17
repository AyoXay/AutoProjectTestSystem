package com.flag.xu.project.system.pojo.enums;

/**
 * Created by Administrator on 2016/11/11.
 */
public enum StandardDataType {
    INT(int.class),
    INTEGER(Integer.class),
    LONG(long.class),
    LONG_CLASS(Long.class),
    DOUBLE(double.class),
    DOUBLE_CLASS(Double.class),
    FLOAT(float.class),
    FLOAT_CLASS(Float.class),
    BOOLEAN(boolean.class),
    BOOLEAN_CLASS(Boolean.class),
    BYTE(byte.class),
    BYTE_CLASS(Byte.class),
    CHAR(char.class),
    CHARACTER(Character.class),
    SHORT(short.class),
    SHORT_CLASS(Short.class);

    private Class type;

    StandardDataType(Class type){
        this.type = type;
    }

    public Class getType() {
        return type;
    }
}
