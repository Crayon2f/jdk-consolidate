package com.crayon2f.jdk.consolidate.pojo;

/**
 * Created by feiFan.gou on 2018/3/7 18:13.
 */
public final class FinalClass {

    private String name;
    private Integer size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public FinalClass(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof FinalClass) {
            FinalClass clazz = (FinalClass) obj;
            return clazz.getName().equals(this.name);
        }
        return false;
    }

    @Override
    public int hashCode() {

        return this.getSize();
    }
}
