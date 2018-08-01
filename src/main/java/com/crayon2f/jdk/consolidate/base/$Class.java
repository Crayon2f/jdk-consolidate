package com.crayon2f.jdk.consolidate.base;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Created by feiFan.gou on 2018/6/12 11:40.
 */
class $Class {

    @Test
    void isInstance() {

        List<Object> objects = Lists.newArrayList(12,"33",9,9L,"33=5");
        objects.stream().filter(String.class::isInstance).forEach(System.out::println);
        System.out.println(String.class.getName());

//        System.out.println(String.class.asSubclass(Integer.class));
    }
}
