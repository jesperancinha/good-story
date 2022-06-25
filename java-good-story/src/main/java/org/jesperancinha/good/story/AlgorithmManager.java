package org.jesperancinha.good.story;

import org.jesperancinha.good.story.avl.AvlTree;
import org.jesperancinha.good.story.flows.FlowManager;
import org.jesperancinha.good.story.intersection.InterNode;
import org.jesperancinha.good.story.splay.SplayTree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Thread.sleep;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by jofisaes on 29/05/2022
 */
public class AlgorithmManager implements AlgorithmInterface {

    private FlowManager flowManager = new FlowManager();

    /**
     * Double iteration of an array of words.
     * Result is the absolute sum of all the differences of sizes between words
     * This function follows has a quadratic big O time complexity of O(n^2) and a space complexity of O(1)
     */
    @Override
    public Integer contentSplitIterateSubtractAndSum(String[] allWords) {
        var sum = 0;
        for (var element : allWords)
            for (int j = allWords.length - 1; j >= 0; j--) {
                sum += abs(element.length() - allWords[j].length());
            }
        return sum;
    }

    /**
     * Counts how many repeated words are in text.
     * If one word is repeated 3 times, that counts as 2 repetitions.
     * The result is a sum of all of these repetitions per word.
     * Time Complexity O(n^2)
     * Space Complexity O(n)
     */

    @Override
    public Long repetitionCount(String content) {
        return stream(content.split(" "))
                .map(word -> word.replaceAll(",", "").replaceAll("\\.", "").replaceAll("\\?", "").toLowerCase())
                .collect(groupingBy(Function.identity(), Collectors.counting()))
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
        return makeWordsList(content).sorted().collect(groupingBy(identity(), Collectors.counting()));
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
     * @param allWords
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
            final var nonPrimes = new boolean[contentLength];
            nonPrimes[1] = true;
            int nonPrimesCount = 1;
            for (var number = 2; number < contentLength; number++) {
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
            final var primesCount = contentLength - nonPrimesCount;
            final var primeNumbers = new int[primesCount];
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
     *
     * @param allWords
     * @return
     */
    @Override
    public SplayTree createSplayTree(String[] allWords) {
        var splayTree = new SplayTree();
        for (var word : allWords) {
            splayTree.insert(word);
        }
        return splayTree;
    }

    /**
     * Quick Sort
     * Time complexity O(n * log n)
     * Space complexity O(log n)
     *
     * @param allWords
     * @return
     */
    @Override
    public List<String> quickSort(List<String> allWords) {
        if (allWords.size() <= 1) {
            return allWords;
        }
        var left = new ArrayList<String>();
        var right = new ArrayList<String>();
        var top = allWords.get(0);
        var length = allWords.size() - 1;

        for (int i = 1; i < length; i++) {
            if (allWords.get(i).compareTo(top) < 0) {
                left.add(allWords.get(i));
            } else {
                right.add(allWords.get(i));
            }

        }
        var targetArray = new ArrayList<>(quickSort(left));
        targetArray.add(top);
        targetArray.addAll(quickSort(right));
        return targetArray;
    }


    /**
     * Make Virtual Thread go through states
     * RUNNING
     * PARKING
     * RUNNING
     *
     * @param words
     * @return
     */
    @Override
    public String makeTextFromWordFlow(List<String> words) {
        return flowManager.readWordFlowBack(words);
    }

    /**
     * Creates a {@link InterNode} cascading linked node tree from two sentences.
     * The intersection node is where both sentences have exactly the same words in exactly the same order.
     * Time complexity is O(max(n,m)) => O(n)
     * Space complexity is O(n + m) => O(n)
     *
     * @param sentenceLeft
     * @param sentenceRight
     * @return
     */
    @Override
    public List<InterNode> createIntersectionWordList(String sentenceLeft, String sentenceRight) {
        final var wordsLeft = sentenceLeft.split(" ");
        final var wordsRight = sentenceRight.split(" ");
        final var leftI = wordsLeft.length - 1;
        final var rightI = wordsRight.length - 1;
        InterNode interCurrNode = null;
        InterNode leftCurrNode = null;
        InterNode rightCurrNode = null;
        for (int i = 0; i <= max(leftI, rightI); i++) {
            var currL = leftI - i;
            var currR = rightI - i;
            if (currL >= 0 && currR > 0 && Objects.equals(wordsLeft[currL], wordsRight[currR])) {
                if (interCurrNode == null) {
                    interCurrNode = new InterNode(wordsLeft[currL]);
                    leftCurrNode = interCurrNode;
                    rightCurrNode = interCurrNode;
                } else {
                    var prevNode = new InterNode(wordsLeft[currL]);
                    prevNode.next = interCurrNode;
                    interCurrNode = prevNode;
                    leftCurrNode = interCurrNode;
                    rightCurrNode = interCurrNode;
                }
            } else {
                if (currL >= 0) {
                    var prevNode = new InterNode(wordsLeft[currL]);
                    prevNode.next = leftCurrNode;
                    leftCurrNode = prevNode;
                }
                if (currR >= 0) {
                    var prevNode = new InterNode(wordsRight[currR]);
                    prevNode.next = rightCurrNode;
                    rightCurrNode = prevNode;
                }
            }

        }
        return List.of(leftCurrNode, rightCurrNode);
    }

    /**
     * An example of an IO operation that doesn't seem to change the behaviour of Virtual Threads.
     * Goes through state
     * RUNNING
     * PARKING
     *
     * @param words
     * @return
     */
    @Override
    public String saveWords(List<String> words) {
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final File tempFile;
        try {
            tempFile = File.createTempFile("test", "txt");
            tempFile.deleteOnExit();
            try (var fos = new FileOutputStream(tempFile)) {
                words.forEach(w -> {
                    try {
                        fos.write(w.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "saveWords OK!";
    }


    /**
     * This method doesn't seem to be doing much, but notice that it is synchronized.
     * Synchronized in virtual threads, generates the need to Park threads while they wait
     * Makes Virtual Thread go through states
     * RUNNING
     * PARKING
     * PINNED
     * RUNNING
     *
     * @param words
     * @return
     */
    public synchronized String saveWordsParking(List<String> words) {
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var options = new HashSet<StandardOpenOption>();
        options.add(CREATE);
        options.add(APPEND);
        try {
            var tempFile = File.createTempFile("test", "txt");
            tempFile.deleteOnExit();
            var path = Paths.get(tempFile.getAbsolutePath());
            var fileChannel = FileChannel.open(path, options);
            words.forEach(w -> {
                try {
                    fileChannel.write(ByteBuffer.wrap(w.getBytes(StandardCharsets.UTF_8)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            fileChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "saveWords OK!";
    }

    /**
     * Goes through states
     * RUNNING
     * YIELDING
     *
     * @return
     */
    @Override
    public String wait0Nanos() {
        try {
            sleep(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Ok";
    }

    /**
     * Goes through states
     * RUNNING
     * PARKING
     * YIELDING
     *
     * @return
     */ @Override
    public String wait100Mills() {
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Ok";    }

    private static boolean filterWords(String possibleWord) {
        return possibleWord.matches("[a-zA-Z]+");
    }
}
