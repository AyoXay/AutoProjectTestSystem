package com.flag.xu.project.system.pojo;

import com.flag.xu.project.system.annotation.Property;
import com.flag.xu.project.system.annotation.PropertyIgnore;

/**
 * @Authuor Administrator
 * @Create 2016-11-11-10:51
 */
public class XmlTestPojo {
    private String name;
    @PropertyIgnore
    private boolean bool;
    @Property(value = "id")
    private int anInt;
    @PropertyIgnore
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    @Override
    public String toString() {
        return "Name : " + name + "\tbool : " + bool + "\tanInt : " + anInt;
    }
}
