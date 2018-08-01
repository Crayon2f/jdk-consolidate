package com.crayon2f.jdk.consolidate.base;

import lombok.ToString;

import java.io.Serializable;

/**
 * Created by feiFan.gou on 2018/6/8 16:55.
 * 不仅此类需要 implements Serializable 其属性类型也需要,否则会抛出 WriteAbortedException: writing aborted;
 */
@ToString
class PojoForSerial implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Integer age;

    public PojoForSerial(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
