package org.jesperancinha.good.story.avl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.max;

/**
 * Created by jofisaes on 29/05/2022
 */
public class AvlNodeManager {
    private AvlNode parentNode;

    public AvlNodeManager() {
        parentNode = null;
    }

    public AvlNode getParentNode() {
        return parentNode;
    }

    public void insertWord(String word) {
        parentNode = insertWord(word, parentNode);
    }

    private int getNodeHeight(AvlNode avlNode) {
        return avlNode == null ? -1 : avlNode.height();
    }

    private AvlNode insertWord(String word, AvlNode avlNode) {
        var processedAvlNode = processNode(word, avlNode);
        processedAvlNode.setHeight(max(getNodeHeight(processedAvlNode.leftNode()), getNodeHeight(processedAvlNode.rightNode())) + 1);
        return processedAvlNode;
    }

    private AvlNode processNode(String word, AvlNode avlNode) {
        if (avlNode == null)
            return new AvlNode(word);
        else if (word.compareTo(avlNode.word()) < 0) {
            avlNode.setLeftNode(insertWord(word, avlNode.leftNode()));
            if (getNodeHeight(avlNode.leftNode()) - getNodeHeight(avlNode.rightNode()) == 2)
                return rotateLeft(word, avlNode);
        } else if (word.compareTo(avlNode.word()) > 0) {
            avlNode.setRightNode(insertWord(word, avlNode.rightNode()));
            if (getNodeHeight(avlNode.rightNode()) - getNodeHeight(avlNode.leftNode()) == 2)
                return rotateRight(word, avlNode);
        }
        return avlNode;
    }

    private AvlNode rotateRight(String word, AvlNode avlNode) {
        if (word.compareTo(avlNode.rightNode().word()) > 0)
            return rotateRight(avlNode);
        else
            return rotateRightLeft(avlNode);
    }

    private AvlNode rotateLeft(String word, AvlNode avlNode) {
        if (word.compareTo(avlNode.leftNode().word()) < 0)
            return rotateLeft(avlNode);
        else
            return rotateLeftRight(avlNode);
    }

    private AvlNode rotateLeft(AvlNode avlNode) {
        var leftNode = avlNode.leftNode();
        avlNode.setLeftNode(leftNode.rightNode());
        leftNode.setRightNode(avlNode);
        avlNode.setHeight(max(getNodeHeight(avlNode.leftNode()), getNodeHeight(avlNode.rightNode())) + 1);
        leftNode.setHeight(max(getNodeHeight(leftNode.leftNode()), avlNode.height()) + 1);
        return leftNode;
    }

    private AvlNode rotateRight(AvlNode avlNode) {
        var rightNode = avlNode.rightNode();
        avlNode.setRightNode(rightNode.leftNode());
        rightNode.setLeftNode(avlNode);
        avlNode.setHeight(max(getNodeHeight(avlNode.leftNode()), getNodeHeight(avlNode.rightNode())) + 1);
        rightNode.setHeight(max(getNodeHeight(rightNode.rightNode()), avlNode.height()) + 1);
        return rightNode;
    }

    private AvlNode rotateLeftRight(AvlNode avlNode) {
        avlNode.setLeftNode(rotateRight(avlNode.leftNode()));
        return rotateLeft(avlNode);
    }

    private AvlNode rotateRightLeft(AvlNode avlNode) {
        avlNode.setRightNode(rotateLeft(avlNode.rightNode()));
        return rotateRight(avlNode);
    }

    public int getNodeCount() {
        return getNodeCount(parentNode);
    }

    private int getNodeCount(AvlNode head) {
        if (head == null)
            return 0;
        else {
            return 1 + getNodeCount(head.leftNode()) + getNodeCount(head.rightNode());
        }
    }

    public boolean searchWord(String word) {
        return searchWord(parentNode, word);
    }

    private boolean searchWord(AvlNode head, String word) {
        boolean check = false;
        var currentHead = head;
        while ((currentHead != null) && !check) {
            String headWord = currentHead.word();
            if (word.compareTo(headWord) < 0)
                currentHead = currentHead.leftNode();
            else if (word.compareTo(headWord) > 0)
                currentHead = currentHead.rightNode();
            else {
                return true;
            }
            check = searchWord(currentHead, word);
        }
        return check;
    }

    public void sortedTraversal() {
        logger.info(">>>>>>> Sorted traversal");
        sortedTraversal(parentNode);
    }

    private void sortedTraversal(AvlNode head) {
        if (head != null) {
            sortedTraversal(head.leftNode());
            logger.info(head.word() + " ");
            sortedTraversal(head.rightNode());
        }
    }

    public void unsortedTopDownTraversal() {
        logger.info(">>>>>>> Top Down traversal");
        unsortedTopDownTraversal(parentNode);
    }

    private void unsortedTopDownTraversal(AvlNode head) {
        if (head != null) {
            logger.info(head.word() + " ");
            unsortedTopDownTraversal(head.leftNode());
            unsortedTopDownTraversal(head.rightNode());
        }
    }

    public void unsortedBottomUpTraversal() {
        logger.info(">>>>>>> Bottom Up traversal");
        unsortedBottomUpTraversal(parentNode);
    }

    private void unsortedBottomUpTraversal(AvlNode head) {
        if (head != null) {
            unsortedBottomUpTraversal(head.leftNode());
            unsortedBottomUpTraversal(head.rightNode());
            logger.info(head.word() + " ");
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(AvlNodeManager.class);

    @Override
    public String toString() {
        sortedTraversal();
        return String.format("The head word is %s", parentNode.word());
    }
}