package com.algorithm;

import java.util.*;

/**
 * 拓扑排序
 *
 * @author read
 */
public class TopologicalSort {
    public static void main(String[] args) {
        System.out.println(canFinish(3, new int[][]{{1, 0}, {2, 1}}));
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        int count = 0;
        int[] a = new int[numCourses];
        List<List<Integer>> edgeList = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edgeList.add(new ArrayList<>());
        }
        for (int[] rel : prerequisites) {
            a[rel[0]]++;
            edgeList.get(rel[1]).add(rel[0]);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int course = 0; course < numCourses; course++) {
            if (a[course] == 0) {
                queue.add(course);
            }
        }
        while (!queue.isEmpty()) {
            Integer pop = queue.poll();
            count++;
            for (int i : edgeList.get(pop)) {
                if (--a[i] == 0) {
                    queue.offer(i);
                }
            }
        }
        return count == numCourses;
    }
}
