package org.intelligentjava.algos.trees;

public class RedBlackTree extends AbstractSelfBalancingBinarySearchTree {

    @Override
    protected Node createNode(int value, Node parent, Node left, Node right) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void transplant(Node deleteNode, Node right) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected enum ColorEnum {
        RED,
        BLACK
    }

    protected static final RedBlackNode nilNode = new RedBlackNode(null, null, null, null, ColorEnum.BLACK);

    @Override
    public Node insert(int element) {
        Node newNode = super.insert(element);
        newNode.left = nilNode;
        newNode.right = nilNode;
        root.parent = nilNode;
        fixInsertViolation((RedBlackNode) newNode);
        return newNode;
    }

    @Override
    protected Node delete(Node deleteNode) {
        if (deleteNode == null || deleteNode == nilNode) {
            return null;
        }

        Node replaceNode = performDeletion(deleteNode);
        size--;

        if (((RedBlackNode) deleteNode).color == ColorEnum.BLACK) {
            fixDeleteViolation((RedBlackNode) replaceNode);
        }
        return replaceNode;
    }

    private Node performDeletion(Node deleteNode) {
        Node replaceNode;
        Node movedUpNode = deleteNode;
        ColorEnum originalColor = ((RedBlackNode) movedUpNode).color;

        if (deleteNode.left == nilNode) {
            replaceNode = deleteNode.right;
            transplant(deleteNode, deleteNode.right);
        } else if (deleteNode.right == nilNode) {
            replaceNode = deleteNode.left;
            transplant(deleteNode, deleteNode.left);
        } else {
            movedUpNode = getMinimum(deleteNode.right);
            originalColor = ((RedBlackNode) movedUpNode).color;
            replaceNode = movedUpNode.right;
            if (movedUpNode.parent == deleteNode) {
                replaceNode.parent = movedUpNode;
            } else {
                transplant(movedUpNode, movedUpNode.right);
                movedUpNode.right = deleteNode.right;
                movedUpNode.right.parent = movedUpNode;
            }
            transplant(deleteNode, movedUpNode);
            movedUpNode.left = deleteNode.left;
            movedUpNode.left.parent = movedUpNode;
            ((RedBlackNode) movedUpNode).color = ((RedBlackNode) deleteNode).color;
        }
        return replaceNode;
    }

    private void fixDeleteViolation(RedBlackNode node) {
        while (node != root && isBlack(node)) {
            if (node == node.parent.left) {
                handleLeftSiblingCases(node);
            } else {
                handleRightSiblingCases(node);
            }
        }
        node.color = ColorEnum.BLACK;
    }

    private void handleLeftSiblingCases(RedBlackNode node) {
        RedBlackNode sibling = (RedBlackNode) node.parent.right;
        if (isRed(sibling)) {
            sibling.color = ColorEnum.BLACK;
            ((RedBlackNode) node.parent).color = ColorEnum.RED;
            rotateLeft(node.parent);
            sibling = (RedBlackNode) node.parent.right;
        }
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            sibling.color = ColorEnum.RED;
            node = (RedBlackNode) node.parent;
        } else {
            if (isBlack(sibling.right)) {
                sibling.left.color = ColorEnum.BLACK;
                sibling.color = ColorEnum.RED;
                rotateRight(sibling);
                sibling = (RedBlackNode) node.parent.right;
            }
            sibling.color = ((RedBlackNode) node.parent).color;
            ((RedBlackNode) node.parent).color = ColorEnum.BLACK;
            sibling.right.color = ColorEnum.BLACK;
            rotateLeft(node.parent);
            node = (RedBlackNode) root;
        }
    }

    private void handleRightSiblingCases(RedBlackNode node) {
        RedBlackNode sibling = (RedBlackNode) node.parent.left;
        if (isRed(sibling)) {
            sibling.color = ColorEnum.BLACK;
            ((RedBlackNode) node.parent).color = ColorEnum.RED;
            rotateRight(node.parent);
            sibling = (RedBlackNode) node.parent.left;
        }
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            sibling.color = ColorEnum.RED;
            node = (RedBlackNode) node.parent;
        } else {
            if (isBlack(sibling.left)) {
                sibling.right.color = ColorEnum.BLACK;
                sibling.color = ColorEnum.RED;
                rotateLeft(sibling);
                sibling = (RedBlackNode) node.parent.left;
            }
            sibling.color = ((RedBlackNode) node.parent).color;
            ((RedBlackNode) node.parent).color = ColorEnum.BLACK;
            sibling.left.color = ColorEnum.BLACK;
            rotateRight(node.parent);
            node = (RedBlackNode) root;
        }
    }

    private void fixInsertViolation(RedBlackNode node) {
        while (node.parent != root && isRed(node.parent)) {
            if (node.parent == node.parent.parent.left) {
                handleLeftUncleCases(node);
            } else {
                handleRightUncleCases(node);
            }
        }
        ((RedBlackNode) root).color = ColorEnum.BLACK;
    }

    private void handleLeftUncleCases(RedBlackNode node) {
        RedBlackNode uncle = (RedBlackNode) node.parent.parent.right;
        if (isRed(uncle)) {
            recolorForInsert(node, uncle);
        } else {
            if (node == node.parent.right) {
                node = (RedBlackNode) node.parent;
                rotateLeft(node);
            }
            node.parent.color = ColorEnum.BLACK;
            node.parent.parent.color = ColorEnum.RED;
            rotateRight(node.parent.parent);
        }
    }

    private void handleRightUncleCases(RedBlackNode node) {
        RedBlackNode uncle = (RedBlackNode) node.parent.parent.left;
        if (isRed(uncle)) {
            recolorForInsert(node, uncle);
        } else {
            if (node == node.parent.left) {
                node = (RedBlackNode) node.parent;
                rotateRight(node);
            }
            node.parent.color = ColorEnum.BLACK;
            node.parent.parent.color = ColorEnum.RED;
            rotateLeft(node.parent.parent);
        }
    }

    private void recolorForInsert(RedBlackNode node, RedBlackNode uncle) {
        node.parent.color = ColorEnum.BLACK;
        uncle.color = ColorEnum.BLACK;
        node.parent.parent.color = ColorEnum.RED;
    }

    private boolean isBlack(Node node) {
        return node == null || ((RedBlackNode) node).color == ColorEnum.BLACK;
    }

    private boolean isRed(Node node) {
        return node != null && ((RedBlackNode) node).color == ColorEnum.RED;
    }
}
