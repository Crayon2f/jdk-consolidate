package com.crayon2f.jdk.consolidate.base;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

/**
 * Created by feiFan.gou on 2018/6/5 20:30.
 */
class BitOperation {

    /**
     * 判断奇偶数
     */
    @Test
    void oddAndEven() {

        Predicate<Integer> isEven = number -> (number & 1) == 0; //是偶数
        System.out.println(isEven.test(12));
        System.out.println(isEven.test(11));
        System.out.println(isEven.test(0));
        System.out.println(isEven.test(-1));
    }

}
