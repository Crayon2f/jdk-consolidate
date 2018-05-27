package com.crayon2f.jdk.consolidate.sort;

import org.junit.Test;

/**
 * 基本思想是：把n个待排序的元素看成一个有序表和无序表。一开始有序表只包含一个元素，无序表中包含n-1个元素。
 * 排序过程中每次从无序表中取出第一个元素，把它依次与有序表中的元素进行比较。然后把它插入到有序表的适当位置，形成新的有序表。
 通俗的讲：假设第一个元素是有序的，后面的元素往前插入，依次进行比较，小的往前挪，大的往后挪，找到合适的位置插入。打个形象的比方就是打扑克牌时的摆牌。
 */
public class InsertionSort extends BaseSort {

    @Test
    public void testInsertSort() {

        printResult("insert sort");
    }

    public void sort(int[] array) {

        int index, temp;
        for (int i = 1; i < array.length; i++) {
            temp = array[i];
            index = i;
            while (index > 0 && temp < array[index - 1]) { //保证没有到首
                array[index] = array[index - 1];
                index --;
            }
            array[index] = temp;
        }
    }


    private void insertSort(int[] arr) {

//        int i, j;
//        int n = arr.length;
//        int target;
//
//        //假定第一个元素被放到了正确的位置上
//        //这样，仅需遍历1 - n-1
//        for (i = 2; i < n; i++) {
//            j = i;
//            target = arr[i];
////            System.out.println(String.format("target ==> %d", target));
//            System.out.println("=================");
//            while (j > 0 && target < arr[j - 1]) {
////                System.out.println(String.format("arr[j] ==> %d", arr[j]));
////                System.out.println(String.format("arr[j - 1] ==> %d", arr[j - 1]));
//                arr[j] = arr[j - 1];
//                j--;
//                System.out.println(Arrays.toString(arr));
//            }
//            arr[j] = target;
//        }


        for (int i = 1; i < arr.length; i++) {
            //插入的数
            int insertVal = arr[i];
            //被插入的位置(准备和前一个数比较)
            int index = i - 1;
            //如果插入的数比被插入的数小
            while (index >= 0 && insertVal < arr[index]) {
                //将把arr[index] 向后移动
                arr[index + 1] = arr[index];
                //让index向前移动
                index--;
            }
            //把插入的数放入合适位置
            arr[index + 1] = insertVal;
        }

    }



}
