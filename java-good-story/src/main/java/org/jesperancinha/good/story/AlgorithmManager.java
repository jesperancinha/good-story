package org.jesperancinha.good.story;

import org.jesperancinha.good.story.avl.AvlTree;
import org.jesperancinha.good.story.splay.SplayTree;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.util.Arrays.stream;
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
    public Integer contentSplitIterateSubtractAndSum(String[] allWords) {
        var sum = 0;
        for (String element : allWords)
            for (int j = allWords.length - 1; j >= 0; j--) {
                sum += abs(element.length() - allWords[j].length());
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
        return stream(content.split(" "))
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
    public String[] findAllUniqueWordsArray(String content) {
        return findAllUniqueWords(content).toArray(new String[0]);
    }

    @Override
    public Stream<String> makeWordsList(String content) {
        return stream(content.split(" ")).sorted().filter(AlgorithmManager::filterWords);
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
    public AvlTree createAvlTree(String[] allWords) {
        var avlTree = new AvlTree();
        for (String word : allWords) {
            avlTree.insertWord(word);
        }
        return avlTree;
    }


    /**
     * Sieve of Eratosthenes applied to secret words find
     * Time complexity is O(n log(log n)) and Space complexity is O(n)
     *
     * @param content
     * @return Secret word based as a concatenation of all characters from the prime indexes.
     */
    @Override
    public String findPrimeSecret(String content) {
        int contentLength = content.length();
        if (contentLength < 2) {
            return "";
        } else {
            boolean[] nonPrimes = new boolean[contentLength];
            nonPrimes[1] = true;
            int nonPrimesCount = 1;
            for (int number = 2; number < contentLength; number++) {
                if (nonPrimes[number]) {
                    continue;
                }
                int multiple = number * 2;
                while (multiple < contentLength) {
                    if (!nonPrimes[multiple]) {
                        nonPrimes[multiple] = true;
                        nonPrimesCount++;
                    }
                    multiple += number;
                }
            }
            final int primesCount = contentLength - nonPrimesCount;
            int[] primeNumbers = new int[primesCount];
            int currentPrime = 0;
            for (int number = 0; number < nonPrimes.length; number++) {
                if (!nonPrimes[number]) {
                    primeNumbers[currentPrime++] = number;
                }
            }
            return stream(primeNumbers).mapToObj(i -> "" + content.charAt(i)).collect(Collectors.joining(""));
        }
    }

    /**
     * Implementation of a simple Splay Tree algotithm
     * This follows a O(log n) time complexity and a O(n) space complexity
     * @param allWords
     * @return
     */
    @Override
    public SplayTree createSplayTree(String[] allWords) {
        var splayTree = new SplayTree();
        for (String word : allWords) {
            splayTree.insert(word);
        }
        return splayTree;
    }

    private static boolean filterWords(String possibleWord) {
        return possibleWord.matches("[a-zA-Z]+");
    }
}
