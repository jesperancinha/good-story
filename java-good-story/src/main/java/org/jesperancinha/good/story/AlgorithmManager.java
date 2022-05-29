package org.jesperancinha.good.story;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.util.function.Function.identity;

/**
 * Created by jofisaes on 29/05/2022
 */
public class AlgorithmManager implements AlgorithmInterface {
    /**
     * Double iteration of an array of words.
     * Result is the absolute sum of all the differences of sizes between words
     * This function follows has a quadratic big O time complexity of O(n^2) and a space complexity of O(1)
     */
    @Override
    public Integer contentSplitIterateSubtractAndSum(String content) {
        var allWords = findAllUniqueWords(content);
        var sum = 0;
        for (String element : allWords)
            for (int j = allWords.size() - 1; j >= 0; j--) {
                sum += abs(element.length() - allWords.get(j).length());
            }
        return sum;
    }

    /**
     * Counts how many repeated words are in text.
     * If one word is repeated 3 times, that counts as 2 repetitions.
     * The result is a sum of all of these repetitions per word.
     */

    @Override
    public Long repetitionCount(String content) {
        return Arrays.stream(content.split(" "))
                .map(word -> word.replaceAll(",", "").replaceAll("\\.", "").replaceAll("\\?", "").toLowerCase())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values().stream().filter(repetitions -> repetitions > 1)
                .reduce(0L, (a, b) -> a + b - 1);
    }

    /**
     * Reverts a string using a space complexity of O(1) and a time complexity of O(n)
     *
     * @param content Content
     * @return Reverted String content
     */
    @Override
    public String revertText(String content) {
        final char[] charArray = content.toCharArray();
        for (int i = 0; i < charArray.length / 2; i++) {
            char c = charArray[i];
            charArray[i] = charArray[charArray.length - i - 1];
            charArray[charArray.length - i - 1] = c;
        }
        return new String(charArray);
    }

    @Override
    public Map<String, Long> findAllUniqueWordsWithCount(String content) {
        return makeWordsList(content).sorted().collect(Collectors.groupingBy(identity(), Collectors.counting()));
    }

    @Override
    public List<String> findAllUniqueWords(String content) {
        return makeWordsList(content).distinct().collect(Collectors.toList());
    }

    @Override
    public Stream<String> makeWordsList(String content) {
        return Arrays.stream(content.split(" ")).sorted().filter(AlgorithmManager::filterWords);
    }

    /**
     * Avl tree algorithm implementation.
     * This algorithm follows a:
     * O(n) complexity in terms of space. The more words there are, the more nodes there will be. Most nodes will carry parent, left and right node information. As the algorithm progresses, space will be used linearly and accordingly. This is the same for worst and average case scenarios.
     * O(log n) complexity in terms of time for search, insert and delete operations. This is the reason this algorithm was invented in the first place. Traversing through the balanced tree, should give us the result we need in an algorithmic fashion. Worst case scenario is O(n) but on average, it is O(log n)
     *
     * @param content
     * @return Avl parent Node
     */
    @Override
    public AvlNode createAvlTree(String content) {
        var allWords = makeWordsList(content);
        return null;
    }

    private static boolean filterWords(String possibleWord) {
        return possibleWord.matches("[a-zA-Z]+");
    }
}
