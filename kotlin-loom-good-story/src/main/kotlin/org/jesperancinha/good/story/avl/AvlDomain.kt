package org.jesperancinha.good.story.avl

import org.slf4j.LoggerFactory

/**
 * Created by jofisaes on 29/05/2022
 */
class AvlNode(
    val word: String,
    var leftNode: AvlNode? = null,
    var rightNode: AvlNode? = null,
    var height: Int = 0
)

class AvlTree {
    var parentNode: AvlNode? = null
        private set

    fun insertWord(word: String) {
        parentNode = insertWord(word, parentNode)
    }

    private fun getNodeHeight(avlNode: AvlNode?): Int {
        return avlNode?.height ?: -1
    }

    private fun insertWord(word: String, avlNode: AvlNode?): AvlNode {
        val processedAvlNode = processNode(word, avlNode)
        processedAvlNode.height = (
                getNodeHeight(processedAvlNode.leftNode).coerceAtLeast(getNodeHeight(processedAvlNode.rightNode)) + 1)
        return processedAvlNode
    }

    private fun processNode(word: String, avlNode: AvlNode?): AvlNode {
        if (avlNode == null) return AvlNode(word) else if (word < avlNode.word) {
            avlNode.leftNode = (insertWord(word, avlNode.leftNode))
            if (getNodeHeight(avlNode.leftNode) - getNodeHeight(avlNode.rightNode) == 2) return rotateLeft(
                word,
                avlNode
            )
        } else if (word.compareTo(avlNode.word) > 0) {
            avlNode.rightNode = (insertWord(word, avlNode.rightNode))
            if (getNodeHeight(avlNode.rightNode) - getNodeHeight(avlNode.leftNode) == 2) return rotateRight(
                word,
                avlNode
            )
        }
        return avlNode
    }

    private fun rotateRight(word: String, avlNode: AvlNode): AvlNode {
        return avlNode.rightNode?.let { if (word > it.word) rotateRight(avlNode) else rotateRightLeft(avlNode) }
            ?: throw RuntimeException("Expected right node!")
    }

    private fun rotateLeft(word: String, avlNode: AvlNode): AvlNode {
        return avlNode.leftNode?.let { if (word < it.word) rotateLeft(avlNode) else rotateLeftRight(avlNode) }
            ?: throw RuntimeException("Expected left node!")
    }

    private fun rotateLeft(avlNode: AvlNode?): AvlNode {
        val leftNode: AvlNode = avlNode?.leftNode ?: throw RuntimeException("Cannot rotate a null left node!")
        avlNode.leftNode = (leftNode.rightNode)
        leftNode.rightNode = (avlNode)
        avlNode.height = (getNodeHeight(avlNode.leftNode).coerceAtLeast(getNodeHeight(avlNode.rightNode)) + 1)
        leftNode.height = (getNodeHeight(leftNode.leftNode).coerceAtLeast(avlNode.height) + 1)
        return leftNode
    }

    private fun rotateRight(avlNode: AvlNode?): AvlNode {
        val rightNode: AvlNode = avlNode?.rightNode ?: throw RuntimeException("Cannot rotate a null right node!")
        avlNode.rightNode = (rightNode.leftNode)
        rightNode.leftNode = (avlNode)
        avlNode.height = (getNodeHeight(avlNode.leftNode).coerceAtLeast(getNodeHeight(avlNode.rightNode)) + 1)
        rightNode.height = (getNodeHeight(rightNode.rightNode).coerceAtLeast(avlNode.height) + 1)
        return rightNode
    }

    private fun rotateLeftRight(avlNode: AvlNode): AvlNode {
        avlNode.leftNode = (rotateRight(avlNode.leftNode))
        return rotateLeft(avlNode)
    }

    private fun rotateRightLeft(avlNode: AvlNode): AvlNode {
        avlNode.rightNode = (rotateLeft(avlNode.rightNode))
        return rotateRight(avlNode)
    }

    val nodeCount: Int
        get() = getNodeCount(parentNode)

    fun getNodeCount(head: AvlNode?): Int {
        return if (head == null) 0 else {
            1 + getNodeCount(head.leftNode) + getNodeCount(head.rightNode)
        }
    }

    fun searchWord(word: String): Boolean {
        return searchWord(parentNode, word)
    }

    private fun searchWord(head: AvlNode?, word: String): Boolean {
        var check = false
        var currentHead = head
        while (currentHead != null && !check) {
            val headWord: String = currentHead.word
            currentHead =
                if (word < headWord) currentHead.leftNode else if (word > headWord) currentHead.rightNode else {
                    return true
                }
            check = searchWord(currentHead, word)
        }
        return check
    }

    fun sortedTraversal() {
        logger.info(">>>>>>> Sorted traversal")
        sortedTraversal(parentNode)
    }

    private fun sortedTraversal(head: AvlNode?) {
        if (head != null) {
            sortedTraversal(head.leftNode)
            logger.info(head.word + " ")
            sortedTraversal(head.rightNode)
        }
    }

    fun unsortedTopDownTraversal() {
        logger.info(">>>>>>> Top Down traversal")
        unsortedTopDownTraversal(parentNode)
    }

    private fun unsortedTopDownTraversal(head: AvlNode?) {
        if (head != null) {
            logger.info(head.word + " ")
            unsortedTopDownTraversal(head.leftNode)
            unsortedTopDownTraversal(head.rightNode)
        }
    }

    fun unsortedBottomUpTraversal() {
        logger.info(">>>>>>> Bottom Up traversal")
        unsortedBottomUpTraversal(parentNode)
    }

    private fun unsortedBottomUpTraversal(head: AvlNode?) {
        if (head != null) {
            unsortedBottomUpTraversal(head.leftNode)
            unsortedBottomUpTraversal(head.rightNode)
            logger.info(head.word + " ")
        }
    }

    override fun toString(): String {
        sortedTraversal()
        return "The head word ${parentNode?.word}"
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AvlTree::class.java)
    }
}