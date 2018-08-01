package com.crayon2f.jdk.consolidate.sort;

import org.junit.jupiter.api.Test;

/**
 * 原理:
 * 选择排序的思想是，每一次从待排序的数据元素中选出最小（或最大）的一个元素，存放在序列的起始位置，直到全部待排序的数据元素排完。
 */
public class SelectSort extends BaseSort {

    @Test
    void test() {

        printResult("select sort");

    }

    @Override
    protected void sort(int[] array) {

        int index; //记录下标
        for (int i = 0; i < array.length; i++) {
            index = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[i]) { //选出最小的
                    index = j;
                }
            }
            if (index != i) { //不等于
                int temp = array[i];
                array[i] = array[index];
                array[index] = temp;
            }
        }
    }
}
