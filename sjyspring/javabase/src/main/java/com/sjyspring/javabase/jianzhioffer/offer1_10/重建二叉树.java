package com.sjyspring.javabase.jianzhioffer.offer1_10;

public class 重建二叉树 {

    public static void main(String[] args) {

        int[] preOrder = {4, 7, 12, 1,3,6, 9, 11, 15};
        int[] inOrder = {1,12, 3, 7,6, 4, 11, 9, 15};
        Node dfs = dfs(preOrder, inOrder, null);
        System.out.println(dfs);
    }


    static int proIndex = 0;
    static int inIndex = 0;

    public static Node dfs(int[] preOrder, int[] inOrder, Node node) {
        if (proIndex >= preOrder.length || inIndex >= inOrder.length || (node != null && inOrder[inIndex] == node.val)) {
            return null;
        }
        Node root = new Node(preOrder[proIndex++]);
        root.left = dfs(preOrder, inOrder, root);
        inIndex++;
        root.right = dfs(preOrder, inOrder, node);
        return root;
    }


    static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

}
