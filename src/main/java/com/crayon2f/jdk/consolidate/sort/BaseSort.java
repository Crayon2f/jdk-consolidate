package com.crayon2f.jdk.consolidate.sort;

import java.util.Arrays;

public abstract class BaseSort {

    private static final int[] array = {100, 4, 3, 5, 7, 2, 6, 1};


    void printResult(String algorithmName) {

        System.out.println("original array ==> " + Arrays.toString(array));
        System.out.println(String.format("====== invoke [%s] ====== ", algorithmName));
        sort(array);
        System.out.println("sorted array ==>  " + Arrays.toString(array));
    }

    protected abstract void sort(int[] array);
}
