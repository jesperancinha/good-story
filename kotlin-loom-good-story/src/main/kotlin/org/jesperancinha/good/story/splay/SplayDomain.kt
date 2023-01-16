package org.jesperancinha.good.story.splay

class SplayNode(var word: String) {
    var parent: SplayNode? = null
    var left: SplayNode? = null
    var right: SplayNode? = null
}

class SplayTree {

    var root: SplayNode? = null
    var treeSize = 0

    fun insertWord(word: String) {
        var topNode = root
        var priorTopNode: SplayNode? = null
        while (topNode != null) {
            priorTopNode = topNode
            topNode = if (topNode.word < word) topNode.right else topNode.left
        }
        topNode = SplayNode(word)
        topNode.parent = priorTopNode
        if (priorTopNode == null) {
            root = topNode
        } else if (priorTopNode.word.compareTo(topNode.word) < 0) {
            priorTopNode.right = topNode
        } else {
            priorTopNode.left = topNode
        }
        splay(topNode)
        treeSize++
    }

    fun find(word: String): SplayNode? {
        var startNode = root
        while (startNode != null) {
            startNode =
                if (startNode.word < word) startNode.right else if (word < startNode.word) startNode.left else return startNode
        }
        return null
    }

    fun remove(word: String) {
        val wordNode = find(word) ?: return
        splay(wordNode)
        if (wordNode.left == null) replace(wordNode, wordNode.right) else if (wordNode.right == null) replace(
            wordNode,
            wordNode.left
        ) else {
            val minimumWordNode = subtreeMinimum(wordNode.right)
            if (minimumWordNode?.parent !== wordNode) {
                replace(minimumWordNode, minimumWordNode?.right)
                minimumWordNode?.right = wordNode.right
                minimumWordNode?.right?.parent = minimumWordNode
            }
            replace(wordNode, minimumWordNode)
            minimumWordNode?.left = wordNode.left
            minimumWordNode?.left?.parent = minimumWordNode
        }
        treeSize--
    }

    private fun leftRotate(nodeToRotate: SplayNode?) {
        val rightNode = nodeToRotate?.right
        if (rightNode != null) {
            nodeToRotate.right = rightNode.left
            if (rightNode.left != null) rightNode.left?.parent = nodeToRotate
            rightNode.parent = nodeToRotate.parent
        }
        if (nodeToRotate?.parent == null) root =
            rightNode else if (nodeToRotate === nodeToRotate.parent?.left) nodeToRotate.parent?.left =
            rightNode else nodeToRotate.parent?.right = rightNode
        if (rightNode != null) rightNode.left = nodeToRotate
        nodeToRotate?.parent = rightNode
    }

    private fun rightRotate(nodeToRotate: SplayNode?) {
        val leftNode = nodeToRotate?.left
        if (leftNode != null) {
            nodeToRotate.left = leftNode.right
            if (leftNode.right != null) leftNode.right?.parent = nodeToRotate
            leftNode.parent = nodeToRotate.parent
        }
        if (nodeToRotate?.parent == null) root =
            leftNode else if (nodeToRotate === nodeToRotate.parent?.left) nodeToRotate.parent?.left =
            leftNode else nodeToRotate.parent?.right = leftNode
        if (leftNode != null) leftNode.right = nodeToRotate
        nodeToRotate?.parent = leftNode
    }

    private fun splay(nodeToSplay: SplayNode) {
        while (nodeToSplay.parent != null) {
            if (nodeToSplay.parent?.parent == null) {
                if (nodeToSplay.parent?.left === nodeToSplay) rightRotate(nodeToSplay.parent) else leftRotate(
                    nodeToSplay.parent
                )
            } else if (nodeToSplay.parent?.left === nodeToSplay && nodeToSplay.parent?.parent?.left === nodeToSplay.parent) {
                rightRotate(nodeToSplay.parent?.parent)
                rightRotate(nodeToSplay.parent)
            } else if (nodeToSplay.parent?.right === nodeToSplay && nodeToSplay.parent?.parent?.right === nodeToSplay.parent) {
                leftRotate(nodeToSplay.parent?.parent)
                leftRotate(nodeToSplay.parent)
            } else if (nodeToSplay.parent?.left === nodeToSplay && nodeToSplay.parent?.parent?.right === nodeToSplay.parent) {
                rightRotate(nodeToSplay.parent)
                leftRotate(nodeToSplay.parent)
            } else {
                leftRotate(nodeToSplay.parent)
                rightRotate(nodeToSplay.parent)
            }
        }
    }

    private fun replace(nodeToReplace: SplayNode?, replacingNode: SplayNode?) {
        if (nodeToReplace?.parent == null) root =
            replacingNode else if (nodeToReplace === nodeToReplace.parent?.left) nodeToReplace.parent?.left =
            replacingNode else nodeToReplace.parent?.right = replacingNode
        if (replacingNode != null) replacingNode.parent = nodeToReplace?.parent
    }

    fun subtreeMinimum(nodeToChange: SplayNode?): SplayNode? {
        var iterator = nodeToChange
        while (iterator?.left != null)
            iterator = iterator.left
        return iterator
    }

    fun subtreeMaximum(nodeToChange: SplayNode?): SplayNode? {
        var iterator = nodeToChange
        while (iterator?.right != null) {
            iterator = iterator.right
        }
        return iterator
    }

    fun minWord(): String? {
        return subtreeMinimum(root)?.word
    }

    fun maxWord(): String? {
        return subtreeMaximum(root)?.word
    }

    fun size(): Long {
        return treeSize.toLong()
    }
}
