package org.jesperancinha.good.story

import com.opencsv.bean.CsvBindByName
import org.jesperancinha.good.story.avl.AvlNodeManager
import java.util.stream.Stream

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
    suspend fun contentSplitIterateSubtractAndSum(content: String): Int
    suspend fun repetitionCount(content: String): Int
    fun revertText(content: String): String
    suspend fun findAllUniqueWordsWithCount(content: String): Map<String, Int>
    suspend fun findAllUniqueWords(content: String): List<String>
    suspend fun makeWordsList(content: String): List<String>
    suspend fun createAvlTree(content: String): AvlNodeManager
}

class AlgorithmManager : AlgorithmInterface {
    /**
     * Counts how many repeated words are in text.
     * If one word is repeated 3 times, that counts as 2 repetitions.
     * The result is a sum of all of these repetitions per word.
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
    override suspend fun contentSplitIterateSubtractAndSum(content: String): Int {
        val allWords = findAllUniqueWords(content)
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

    /**
     * Avl tree algorithm implementation.
     * This algorithm follows a:
     * O(n) complexity in terms of space. The more words there are, the more nodes there will be. Most nodes will carry parent, left and right node information. As the algorithm progresses, space will be used linearly and accordingly. This is the same for worst and average case scenarios.
     * O(log n) complexity in terms of time for search, insert and delete operations. This is the reason this algorithm was invented in the first place. Traversing through the balanced tree, should give us the result we need in an algorithmic fashion. Worst case scenario is O(n) but on average, it is O(log n)
     */
    override suspend fun createAvlTree(content: String): AvlNodeManager {
        val allWords: List<String> = makeWordsList(content)
        val avlNodeManager = AvlNodeManager()
        allWords.forEach { word: String -> avlNodeManager.insertWord(word) }
        return avlNodeManager
    }

    private suspend fun String.filterWords(): Boolean = matches(Regex("[a-zA-Z]+"))

}
