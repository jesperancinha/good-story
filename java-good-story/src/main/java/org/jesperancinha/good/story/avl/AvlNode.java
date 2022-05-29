package org.jesperancinha.good.story.avl;

/**
 * Created by jofisaes on 29/05/2022
 */
public class AvlNode {

    private final String word;
    private AvlNode leftNode;
    private AvlNode rightNode;
    private int height;


    public AvlNode(String word) {
        this.word = word;
    }

    public String word() {
        return word;
    }

    public void setLeftNode(AvlNode leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(AvlNode rightNode) {
        this.rightNode = rightNode;
    }

    public AvlNode leftNode() {
        return leftNode;
    }

    public AvlNode rightNode() {
        return rightNode;
    }

    public int height() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

