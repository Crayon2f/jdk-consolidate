package com.crayon2f.jdk.consolidate.sort;

import org.junit.Test;

/**
 * 原理：比较两个相邻的元素，将值大的元素交换至右端。

 思路：依次比较相邻的两个数，将小数放在前面，大数放在后面。即在第一趟：首先比较第1个和第2个数，将小数放前，大数放后。
 然后比较第2个数和第3个数，将小数放前，大数放后，如此继续，直至比较最后两个数，将小数放前，大数放后。重复第一趟步骤，直至全部排序完成。
 */

public class BubbleSort extends BaseSort {

    @Test
    public void test() {
        printResult("bubble sort");
    }

    @Override
    protected void sort(int[] array) {

        for (int i = 0; i < array.length - 1; i++) { //外层循环控制排序趟数，趟数 = array.length - 1
            //内层循环控制每一趟排序多少次， 因为每次内层循环都会有一个最大的，所以下次的时候就不必比较这些最大的也就是末尾那些(这也是为啥叫冒泡排序)，
            // 所以趟数 = array.length - 1 - i
            for (int j = 0; j < array.length - 1 - i; j++) {
                // 交换位置
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

}
