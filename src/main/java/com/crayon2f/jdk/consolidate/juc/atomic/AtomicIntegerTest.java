package com.crayon2f.jdk.consolidate.juc.atomic;

import com.crayon2f.common.pojo.Data;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by feiFan.gou on 2018/7/13 17:16.
 */
class AtomicIntegerTest {

    @Test
    void test() {

        AtomicInteger index = new AtomicInteger(-1);
        int[] index2 = new int[]{0};
        Data.STRING_LIST.parallelStream().map(ths -> {
            int i = index.addAndGet(1);
            System.out.println("=============================");
            System.out.println("index: "+ i);
            System.out.println("index2: "+ index2[0]);
//            System.out.println(ths);
            index2[0]++;
            return i;
        }).collect(Collectors.toList()).forEach(System.out::print);
    }
}
