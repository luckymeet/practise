package com.algorithm;

import java.util.Arrays;

/**
 * 快速排序
 * @author yuminjun
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] array = new int[]{23, 46, 0, 8, 11, 18};
        sort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    private static void sort(int[] array, int left, int right) {
        if (left < right) {
            int index = doSort(array, left, right);
            sort(array, left, index - 1);
            sort(array, index + 1, right - 1);
        }
    }

    private static int doSort(int[] array, int left, int right) {
        int tem = array[left];
        while (left < right) {
            while (array[right] > tem && left < right) {
                right--;
            }
            array[left] = array[right];
            while (array[left] < tem && left < right) {
                left++;
            }
            array[right] = array[left];
        }
        array[left] = tem;
        return left;
    }

}
