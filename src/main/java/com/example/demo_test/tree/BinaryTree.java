package com.example.demo_test.tree;

public class BinaryTree {
    Node root;
    BinaryTree() {
        root = null;
    }
    void duyetGiua(Node node){
        if (node != null){
            duyetGiua(node.left);
            System.out.print(node.data + " ");
            duyetGiua(node.right);
        }
    }
    void duyetTruoc(Node node){
        if (node != null){
            System.out.print(node.data + " ");
            duyetTruoc(node.left);
            duyetTruoc(node.right);
        }
    }
    void duyetSau(Node node){
        if (node != null) {
            duyetSau(node.left);
            duyetSau(node.right);
            System.out.print(node.data + " ");
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        System.out.println("Duyệt cây theo thứ tự giữa (In-order Traversal):");
        tree.duyetGiua(tree.root);

        System.out.println("\nDuyệt cây theo thứ tự trước (Pre-order Traversal):");
        tree.duyetTruoc(tree.root);

        System.out.println("\nDuyệt cây theo thứ tự sau (Post-order Traversal):");
        tree.duyetSau(tree.root);
    }
}
