package com.solution.test;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Solution {

    class TreeNode {
        /**
         * 父节点
         */
        TreeNode parent;
        /**
         * 当前节点身高体重
         */
        int[] person;
        /**
         * 子节点列表
         */
        Set<TreeNode> next;

        public TreeNode(TreeNode parent, int[] person, Set<TreeNode> next) {
            this.parent = parent;
            this.person = person;
            this.next = next;
        }

        public TreeNode getParent() {
            return parent;
        }

        public int[] getPerson() {
            return person;
        }

        public Set<TreeNode> getNext() {
            return next;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public void setPerson(int[] person) {
            this.person = person;
        }

        public void setNext(Set<TreeNode> next) {
            this.next = next;
        }
    }

    public int bestSeqAtIndex(int[] height, int[] weight) {
        int length = height.length;
        if (length == 0 || length == 1) {
            return length;
        }
        List<TreeNode> treeNodes = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            TreeNode treeNode = new TreeNode(null, new int[]{height[i], weight[i]}, new HashSet<>());
            treeNodes.add(treeNode);
        }
        TreeNode topNode = new TreeNode(null, new int[]{0, 0}, new HashSet<>());
        Set<TreeNode> nodes = Collections.newSetFromMap(new ConcurrentHashMap<>(16));
        nodes.add(topNode);
        for (int i = 0; i < length; i++) {
            bfs(nodes, treeNodes.get(i));
        }
        int depth = maxDepth(topNode);
        return depth - 1;
    }

    private int maxDepth(TreeNode topNode) {
        int maxDepth = 0;
        for (TreeNode node : topNode.getNext()) {
            int depth = maxDepth(node);
            maxDepth = Math.max(maxDepth, depth);
        }
        return maxDepth + 1;
    }

    private void bfs(Set<TreeNode> nodes, TreeNode curNode) {
        int i = 0;
        int[] curPerson = curNode.getPerson();
        Iterator<TreeNode> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            i++;
            TreeNode treeNode = iterator.next();
            int[] person = treeNode.getPerson();
            if (person[0] < curPerson[0] && person[1] < curPerson[1]) {
                Set<TreeNode> next = treeNode.getNext();
                if (next.size() == 0) {
                    insertAsLowerNode(curNode, treeNode);
                    return;
                } else {
                    bfs(next, curNode);
                }
            } else if (person[0] > curPerson[0] && person[1] > curPerson[1]) {
                insertAsHigherNode(curNode, treeNode);
                return;
            } else {
                if (i == nodes.size()) {
                    insertAsRightNode(curNode, treeNode);
                    return;
                }
            }
        }
    }

    private void insertAsLowerNode(TreeNode curNode, TreeNode treeNode) {
        treeNode.getNext().add(curNode);
        curNode.setParent(treeNode);
    }

    private void insertAsRightNode(TreeNode curNode, TreeNode treeNode) {
        TreeNode parent = treeNode.getParent();
        if (null != parent) {
            Set<TreeNode> next = parent.getNext();
            next.add(curNode);
            curNode.setParent(parent);
        }
    }

    private void insertAsHigherNode(TreeNode curNode, TreeNode treeNode) {
        TreeNode parent = treeNode.getParent();
        if (null != parent) {
            Set<TreeNode> next = parent.getNext();
            next.remove(treeNode);
            next.add(curNode);
        }
        curNode.getNext().add(treeNode);
        treeNode.setParent(curNode);
    }

    @Test
    public void test() {
        System.out.println(bestSeqAtIndex(new int[]{2868,5485,1356,1306,6017,8941,7535,4941,6331,6181}, new int[]{5042,3995,7985,1651,5991,7036,9391,428,7561,8594}));
    }

}
