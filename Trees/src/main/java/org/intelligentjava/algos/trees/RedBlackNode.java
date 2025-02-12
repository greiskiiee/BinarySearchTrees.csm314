package org.intelligentjava.algos.trees;

import org.intelligentjava.algos.trees.AbstractBinarySearchTree.Node;
import org.intelligentjava.algos.trees.RedBlackTree.ColorEnum;

class RedBlackNode extends Node {

    public ColorEnum color;

    public RedBlackNode(Integer value, Node parent, Node left, Node right, ColorEnum color) {
        super(value, parent, left, right);
        this.color = color;
    }

    public RedBlackNode(Integer value, ColorEnum color) {
        super(value, null, null, null);
        this.color = color;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public boolean isRed() {
        return color == ColorEnum.RED;
    }

    public boolean isBlack() {
        return color == ColorEnum.BLACK;
    }
}
