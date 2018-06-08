package com.crayon2f.jdk.consolidate.clazz.loader;

import java.util.Random;

class FinalClass {

    public static final int x = 4; //final 修饰的变量如果在编译时候就可以算出来，那么将不会执行static 代码块

    static {
        System.out.println("FinalClass block");
    }
}

class FinalClass2 {

    public static final int x = new Random().nextInt(100); // final 修饰的变量，如果运行时才知道，也就是不是常量，那么static代码将会执行

    static {
        System.out.println("FinalClasss block");
    }
}

public class Test {

    public static void main(String[] args) {
        System.out.println(FinalClass.x);
        System.out.println(FinalClass2.x);
    }

}
