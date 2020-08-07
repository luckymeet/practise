package com.algorithm;

import org.junit.Test;

public class BinarySearchTree {

    class Node {
        private int score;
        private Object value;
        private Node left;
        private Node right;

        public Node(int score) {
            this.score = score;
            this.value = score;
        }

        public Node(int score, Object value) {
            this.score = score;
            this.value = value;
        }

        public Node(int score, Node left, Node right) {
            this.score = score;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + score +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }

    }

    public void insert(Node rootNode, Node node) {
        preOrder(rootNode, node);
    }

    private Node preOrder(Node rootNode, Node node) {
        if (rootNode == null) {
            return node;
        }
        int rootVal = rootNode.score;
        int val = node.score;
        if (val < rootVal) {
            rootNode.left = preOrder(rootNode.left, node);
        } else {
            rootNode.right = preOrder(rootNode.right, node);
        }
        return node;
    }

    public void midOrderPrint(Node node) {
        if (null == node) {
            return;
        }
        midOrderPrint(node.left);
        System.out.println(node.value);
        midOrderPrint(node.right);
    }

    @Test
    public void test() {
        Node node = new Node(5);
        insert(node, new Node(3));
        insert(node, new Node(8));
        insert(node, new Node(4));
        insert(node, new Node(9));
        insert(node, new Node(6));
        insert(node, new Node(2));
        System.out.println(node);
        midOrderPrint(node);
    }
}
