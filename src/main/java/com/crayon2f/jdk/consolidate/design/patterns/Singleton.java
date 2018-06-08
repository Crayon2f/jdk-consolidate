package com.crayon2f.jdk.consolidate.design.patterns;

import lombok.Getter;

/**
 * Created by feiFan.gou on 2018/6/4 17:45.
 */
public class Singleton {

    private Singleton(){}

    public static Singleton getInstance() {

        return EnumSingleton.INSTANCE.getSingleton();
    }

    @Getter
    public enum EnumSingleton {

        INSTANCE;

        private Singleton singleton;

        EnumSingleton() {
            this.singleton = new Singleton();
        }

    }
}
