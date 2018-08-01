package com.crayon2f.jdk.consolidate.lombok;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.Wither;

/**
 * Created by feiFan.gou on 2018/5/7 19:21.
 * @value 修饰的类，是final的，且它的属性也是final的
 */
@Value(staticConstructor = "of")
class LombokValue {

    @Wither(AccessLevel.PUBLIC)
    private String string;
    @NonFinal
    @Wither(AccessLevel.PACKAGE)
    int age;
//    public LombokValue(String string) {
//        this.string = string;
//        this.age = 34;
//    }

    class Child {


    }

    void run() {
        System.out.println(this.age);
        System.out.print(this.string);
    }

    public static void main(String[] args) {
//        LombokValue value = LombokValue.of();
        LombokValue value = new LombokValue("string", 34);
        LombokValue value2 = LombokValue.of("string", 45);
        System.out.println(value.age);
        System.out.println(value.string);
        value.run();

    }
}
