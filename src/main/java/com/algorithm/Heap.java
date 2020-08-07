package com.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Heap {

    private int[] array;
    private int size;

    public Heap(int[] array) {
        this.array = array;
    }

    private void swap(int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    private void up(int index) {
        int father;
        while ((father = (index - 1) / 2) >= 0) {
            if (array[index] < array[father]) {
                swap(index, father);
                index = father;
            } else {
                break;
            }
        }
    }

    private void down(int index) {
        int leftChild;
        while ((leftChild = (index * 2 + 1)) < size) {
            int rightChild = leftChild + 1;
            if (rightChild < size) {
                if (array[leftChild] < array[rightChild]) {
                    if (array[leftChild] < array[index]) {
                        swap(index, leftChild);
                        index = leftChild;
                    } else {
                        break;
                    }
                } else {
                    if (array[rightChild] < array[index]) {
                        swap(index, rightChild);
                        index = rightChild;
                    } else {
                        break;
                    }
                }
            } else {
                if (array[leftChild] < array[index]) {
                    swap(index, leftChild);
                    index = leftChild;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 插入元素
     * <p>从尾部插入，然后进行上浮操作
     *
     * @param val
     */
    public synchronized void push(int val) {
        if (size == array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        array[size] = val;
        up(size++);
    }

    /**
     * 弹出第一个元素
     * <p>返回第一个元素，然后将第一个元素替换换末尾元素，再对其进行下沉操作
     * @return 第一个元素，也是堆中最小元素
     */
    public synchronized int pop() {
        int val = new Integer(array[0]);
        array[0] = array[size - 1];
        down(0);
        array[--size] = 0;
        return val;
    }

    /**
     * 获取第一元素（堆中最小元素）
     *
     * @return
     */
    public Integer peek() {
        return size == 0 ? null : array[0];
    }

    public static void main(String[] args) {
        Heap heap = new Heap(new int[20]);
        for (int i = 0; i < 20; i++) {
            heap.push(new Random().nextInt(100));
        }
        System.out.println(Arrays.toString(heap.array));
        System.out.println("size:" + heap.size);
        int size = new Integer(heap.size);
        for (int i = 0; i < size; i++) {
            System.out.println(heap.pop());
        }
    }

}
