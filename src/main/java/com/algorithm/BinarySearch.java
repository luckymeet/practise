package com.algorithm;

public class BinarySearch {
    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 5, 6, 9, 10, 15, 20};
        int index = binarySearch(array, 9);
        System.out.println(index);
    }

    private static int binarySearch(int[] array, int num) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (num > array[mid]) {
                left = mid + 1;
            } else if (num < array[mid]){
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
