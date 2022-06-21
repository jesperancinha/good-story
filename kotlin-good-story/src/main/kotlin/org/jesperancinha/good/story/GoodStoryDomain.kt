package org.jesperancinha.good.story

import com.opencsv.bean.CsvBindByName
import org.jesperancinha.good.story.avl.AvlTree
import org.jesperancinha.good.story.flows.FlowManager
import org.jesperancinha.good.story.intersection.InterNode
import org.jesperancinha.good.story.splay.SplayTree
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.function.Consumer

/**
 * Created by jofisaes on 28/05/2022
 */
data class FunctionReading(
    @CsvBindByName
    var method: String? = null,
    @CsvBindByName
    var timeComplexity: String? = null,
    @CsvBindByName
    var spaceComplexity: String? = null,
    @CsvBindByName
    var repetition: Long? = -1,
    @CsvBindByName
    var javaDuration: Long? = -1L,
    @CsvBindByName
    var kotlinDuration: Long? = -1L,
    @CsvBindByName
    var machine: String? = null
)

interface AlgorithmInterface {
    suspend fun contentSplitIterateSubtractAndSum(allWords: Array<String>): Int
    suspend fun repetitionCount(content: String): Int
    fun revertText(content: String): String
    suspend fun findAllUniqueWordsWithCount(content: String): Map<String, Int>
    suspend fun findAllUniqueWords(content: String): List<String>
    suspend fun makeWordsList(content: String): List<String>
    suspend fun createAvlTree(allWords: Array<String>): AvlTree
    suspend fun findPrimeSecret(content: String): String
    suspend fun findAllUniqueWordsArray(content: String): Array<String>
    suspend fun createSplayTree(allWords: Array<String>): SplayTree?
    suspend fun quickSort(allWords: List<String>): List<String>
    suspend fun makeTextFromWordFlow(words: List<String>): String
    suspend fun createIntersectionWordList(sentenceLeft: String, sentenceRight: String): List<InterNode>
    fun saveWords(words: List<String>): String?
    fun saveWordsParking(words: List<String>): String?
    fun wait0Nanos(): String?
}

class AlgorithmManager : AlgorithmInterface {

    private val flowManager by lazy { FlowManager() }

    /**
     * Counts how many repeated words are in text.
     * If one word is repeated 3 times, that counts as 2 repetitions.
     * The result is a sum of all of these repetitions per word.
     * Time Complexity O(n^2)
     * Space Complexity O(n)
     */
    override suspend fun repetitionCount(content: String) = content.split(" ")
        .map { it.replace(",", "").replace(".", "").replace("?", "").lowercase() }
        .groupingBy { it }.eachCount()
        .filter { it.value > 1 }
        .map { it.value - 1 }
        .sum()

    /**
     * Double iteration of an array of words.
     * Result is the absolute sum of all the differences of sizes between words
     * This function follows has a quadratic big O time complexity notation of O(n^2) and a space complexity of O(1)
     */
    override suspend fun contentSplitIterateSubtractAndSum(allWords: Array<String>): Int {
        var sum = 0;
        for (element in allWords)
            for (j in allWords.size - 1 downTo 0) {
                sum += kotlin.math.abs(element.length - allWords[j].length)
            }
        return sum;
    }


    /**
     * Reverts a string using a space complexity of O(1) and a time complexity of O(n)
     *
     * @param content Content
     * @return Reverted String content
     */
    override fun revertText(content: String): String {
        val charArray = content.toCharArray()
        for (i in 0 until (charArray.size / 2)) {
            val c = charArray[i]
            charArray[i] = charArray[charArray.size - i - 1]
            charArray[charArray.size - i - 1] = c
        }
        return String(charArray)
    }

    override suspend fun findAllUniqueWordsWithCount(content: String): Map<String, Int> = makeWordsList(content)
        .sorted()
        .groupingBy { it }
        .eachCount()

    override suspend fun findAllUniqueWords(content: String): List<String> =
        makeWordsList(content)
            .distinct()

    override suspend fun makeWordsList(content: String): List<String> =
        content.split(" ")
            .sorted()
            .filter {
                it.filterWords()
            }

    override suspend fun findAllUniqueWordsArray(content: String): Array<String> =
        makeWordsList(content).toTypedArray()

    /**
     * Implementation of a simple Splay Tree algotithm
     * This follows a O(log n) time complexity and a O(n) space complexity
     * @param allWords
     * @return
     */
    override suspend fun createSplayTree(allWords: Array<String>): SplayTree {
        val splayTree = SplayTree()
        allWords.forEach { word: String -> splayTree.insertWord(word) }
        return splayTree
    }

    /**
     * Quick Sort
     * Time complexity O(n * log n)
     * Space complexity O(log n)
     **/
    override suspend fun quickSort(allWords: List<String>): List<String> {
        if (allWords.size <= 1) {
            return allWords
        }
        val left = ArrayList<String>()
        val right = ArrayList<String>()
        val top = allWords[0]
        val length = allWords.size - 1

        for (i in 1 until length) {
            if (allWords[i] < top) {
                left.add(allWords[i])
            } else {
                right.add(allWords[i])
            }
        }
        return quickSort(left) + top + quickSort(right)
    }

    override suspend fun makeTextFromWordFlow(words: List<String>) = flowManager.readWordFlowBack(words)

    /**
     * Avl tree algorithm implementation.
     * This algorithm follows a:
     * O(n) complexity in terms of space. The more words there are, the more nodes there will be. Most nodes will carry parent, left and right node information. As the algorithm progresses, space will be used linearly and accordingly. This is the same for worst and average case scenarios.
     * O(log n) complexity in terms of time for search, insert and delete operations. This is the reason this algorithm was invented in the first place. Traversing through the balanced tree, should give us the result we need in an algorithmic fashion. Worst case scenario is O(n) but on average, it is O(log n)
     */
    override suspend fun createAvlTree(allWords: Array<String>): AvlTree {
        val avlTree = AvlTree()
        allWords.forEach { word: String -> avlTree.insertWord(word) }
        return avlTree
    }


    /**
     * Sieve of Eratosthenes applied to secret words find
     * Time complexity is O(n log(log n)) and Space complexity is O(n)
     * @param content
     * @return Secret word based as a concatenation of all characters from the prime indexes.
     */
    override suspend fun findPrimeSecret(content: String): String {
        val contentLength = content.length
        return if (contentLength < 2) {
            ""
        } else {
            val nonPrimes = BooleanArray(contentLength)
            nonPrimes[1] = true
            var nonPrimesCount = 1
            for (number in 2 until contentLength) {
                if (nonPrimes[number]) {
                    continue
                }
                var multiple = number * 2
                while (multiple < contentLength) {
                    if (!nonPrimes[multiple]) {
                        nonPrimes[multiple] = true
                        nonPrimesCount++
                    }
                    multiple += number
                }
            }
            val primesCount = contentLength - nonPrimesCount
            val primeNumbers = IntArray(primesCount)
            var currentPrime = 0
            for (number in nonPrimes.indices) {
                if (!nonPrimes[number]) {
                    primeNumbers[currentPrime++] = number
                }
            }
            primeNumbers.map { i -> content[i] }.joinToString("")
        }
    }

    /**
     * Creates a [InterNode] cascading linked node tree from two sentences.
     * The intersection node is where both sentences have exactly the same words in exactly the same order.
     * Time complexity is O(max(n,m)) => O(n)
     * Space complexity is O(n + m) => O(n)
     * @param sentenceLeft
     * @param sentenceRight
     * @return
     */
    override suspend fun createIntersectionWordList(sentenceLeft: String, sentenceRight: String): List<InterNode> {
        val wordsLeft = sentenceLeft.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val wordsRight = sentenceRight.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val leftI = wordsLeft.size - 1
        val rightI = wordsRight.size - 1
        var interCurrNode: InterNode? = null
        var leftCurrNode: InterNode? = null
        var rightCurrNode: InterNode? = null
        for (i in 0..leftI.coerceAtLeast(rightI)) {
            val currL = leftI - i
            val currR = rightI - i
            if (currL >= 0 && currR > 0 && wordsLeft[currL] == wordsRight[currR]) {
                if (interCurrNode == null) {
                    interCurrNode = InterNode(wordsLeft[currL])
                    leftCurrNode = interCurrNode
                    rightCurrNode = interCurrNode
                } else {
                    val prevNode = InterNode(wordsLeft[currL])
                    prevNode.next = interCurrNode
                    interCurrNode = prevNode
                    leftCurrNode = interCurrNode
                    rightCurrNode = interCurrNode
                }
            } else {
                if (currL >= 0) {
                    val prevNode = InterNode(wordsLeft[currL])
                    prevNode.next = leftCurrNode
                    leftCurrNode = prevNode
                }
                if (currR >= 0) {
                    val prevNode = InterNode(wordsRight[currR])
                    prevNode.next = rightCurrNode
                    rightCurrNode = prevNode
                }
            }
        }
        return if (leftCurrNode != null && rightCurrNode != null) listOf(
            leftCurrNode,
            rightCurrNode
        ) else throw RuntimeException("Something went wrong!")
    }

    /**
     *
     */
    override fun saveWords(words: List<String>): String? {
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        val tempFile: File
        try {
            tempFile = File.createTempFile("test", "txt")
            tempFile.deleteOnExit()
            FileOutputStream(tempFile).use { fos ->
                words.forEach(Consumer { w: String ->
                    try {
                        fos.write(w.toByteArray(StandardCharsets.UTF_8))
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    }
                })
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return "saveWords OK!"
    }


    /**
     *
     */
    @Synchronized
    override fun saveWordsParking(words: List<String>): String? {
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        val options: MutableSet<StandardOpenOption> = HashSet()
        options.add(StandardOpenOption.CREATE)
        options.add(StandardOpenOption.APPEND)
        try {
            val tempFile = File.createTempFile("test", "txt")
            tempFile.deleteOnExit()
            val path = Paths.get(tempFile.absolutePath)
            val fileChannel = FileChannel.open(path, options)
            words.forEach(Consumer { w: String ->
                try {
                    fileChannel.write(ByteBuffer.wrap(w.toByteArray(StandardCharsets.UTF_8)))
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            })
            fileChannel.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return "saveWords OK!"
    }


    /**
     * Makes worker go through these states:
     * BLOCKING
     * PARKING
     * CPU_ACQUIRED
     * DORMANT
     */
    override fun wait0Nanos(): String {
        try {
            Thread.sleep(0)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        return "Ok"
    }

    private suspend fun String.filterWords(): Boolean = matches(Regex("[a-zA-Z]+"))

}
