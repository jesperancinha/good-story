package org.jesperancinha.good.story.splay;

/**
 * Created by jofisaes on 30/05/2022
 */
public class SplayTree {

    SplayNode root;

    Integer treeSize = 0;


    public void insert(String word) {
        SplayNode topNode = root;
        SplayNode priorTopNode = null;

        while (topNode != null) {
            priorTopNode = topNode;
            if (topNode.word.compareTo(word) < 0) topNode = topNode.right;
            else topNode = topNode.left;
        }

        topNode = new SplayNode(word);
        topNode.parent = priorTopNode;

        if (priorTopNode == null) {
            root = topNode;
        } else if (priorTopNode.word.compareTo(topNode.word) < 0) {
            priorTopNode.right = topNode;
        } else {
            priorTopNode.left = topNode;
        }

        splay(topNode);
        treeSize++;
    }

    public SplayNode find(String word) {
        SplayNode startNode = root;
        while (startNode != null) {
            if (startNode.word.compareTo(word) < 0) startNode = startNode.right;
            else if (word.compareTo(startNode.word) < 0) startNode = startNode.left;
            else return startNode;
        }
        return null;
    }

    public void remove(String word) {
        SplayNode wordNode = find(word);
        if (wordNode == null) return;

        splay(wordNode);

        if (wordNode.left == null) replace(wordNode, wordNode.right);
        else if (wordNode.right == null) replace(wordNode, wordNode.left);
        else {
            SplayNode minimumWordNode = subtreeMinimum(wordNode.right);
            if (minimumWordNode.parent != wordNode) {
                replace(minimumWordNode, minimumWordNode.right);
                minimumWordNode.right = wordNode.right;
                minimumWordNode.right.parent = minimumWordNode;
            }
            replace(wordNode, minimumWordNode);
            minimumWordNode.left = wordNode.left;
            minimumWordNode.left.parent = minimumWordNode;
        }
        treeSize--;
    }

    private void leftRotate(SplayNode nodeToRotate) {
        SplayNode rightNode = nodeToRotate.right;
        if (rightNode != null) {
            nodeToRotate.right = rightNode.left;
            if (rightNode.left != null) rightNode.left.parent = nodeToRotate;
            rightNode.parent = nodeToRotate.parent;
        }

        if (nodeToRotate.parent == null)
            root = rightNode;
        else if (nodeToRotate == nodeToRotate.parent.left) nodeToRotate.parent.left = rightNode;
        else nodeToRotate.parent.right = rightNode;
        if (rightNode != null) rightNode.left = nodeToRotate;
        nodeToRotate.parent = rightNode;
    }

    private void rightRotate(SplayNode nodeToRotate) {
        SplayNode leftNode = nodeToRotate.left;
        if (leftNode != null) {
            nodeToRotate.left = leftNode.right;
            if (leftNode.right != null) leftNode.right.parent = nodeToRotate;
            leftNode.parent = nodeToRotate.parent;
        }
        if (nodeToRotate.parent == null) root = leftNode;
        else if (nodeToRotate == nodeToRotate.parent.left) nodeToRotate.parent.left = leftNode;
        else nodeToRotate.parent.right = leftNode;
        if (leftNode != null) leftNode.right = nodeToRotate;
        nodeToRotate.parent = leftNode;
    }

    private void splay(SplayNode nodeToSplay) {
        while (nodeToSplay.parent != null) {
            if (nodeToSplay.parent.parent == null) {
                if (nodeToSplay.parent.left == nodeToSplay) rightRotate(nodeToSplay.parent);
                else leftRotate(nodeToSplay.parent);
            } else if (nodeToSplay.parent.left == nodeToSplay && nodeToSplay.parent.parent.left == nodeToSplay.parent) {
                rightRotate(nodeToSplay.parent.parent);
                rightRotate(nodeToSplay.parent);
            } else if (nodeToSplay.parent.right == nodeToSplay && nodeToSplay.parent.parent.right == nodeToSplay.parent) {
                leftRotate(nodeToSplay.parent.parent);
                leftRotate(nodeToSplay.parent);
            } else if (nodeToSplay.parent.left == nodeToSplay && nodeToSplay.parent.parent.right == nodeToSplay.parent) {
                rightRotate(nodeToSplay.parent);
                leftRotate(nodeToSplay.parent);
            } else {
                leftRotate(nodeToSplay.parent);
                rightRotate(nodeToSplay.parent);
            }
        }
    }

    private void replace(SplayNode nodeToReplace, SplayNode replacingNode) {
        if (nodeToReplace.parent == null) root = replacingNode;
        else if (nodeToReplace == nodeToReplace.parent.left) nodeToReplace.parent.left = replacingNode;
        else nodeToReplace.parent.right = replacingNode;
        if (replacingNode != null) replacingNode.parent = nodeToReplace.parent;
    }

    public SplayNode subtreeMinimum(SplayNode nodeToChange) {
        while (nodeToChange.left != null) nodeToChange = nodeToChange.left;
        return nodeToChange;
    }

    public SplayNode subtreeMaximum(SplayNode nodeToChange) {
        while (nodeToChange.right != null) nodeToChange = nodeToChange.right;
        return nodeToChange;
    }

    public String minWord() {
        return subtreeMinimum(root).word;
    }

    public String maxWord() {
        return subtreeMaximum(root).word;
    }


    public long size() {
        return treeSize;
    }

    public SplayNode getRoot() {
        return root;
    }
}

