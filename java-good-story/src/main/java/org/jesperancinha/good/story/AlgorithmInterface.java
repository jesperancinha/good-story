package org.jesperancinha.good.story;

import org.jesperancinha.good.story.avl.AvlNodeManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface AlgorithmInterface {
    Integer contentSplitIterateSubtractAndSum(String[] allWords);

    Long repetitionCount(String content);

    String revertText(String content);

    Map<String, Long> findAllUniqueWordsWithCount(String content);

    List<String> findAllUniqueWords(String content);

    String[] findAllUniqueWordsArray(String content);

    Stream<String> makeWordsList(String content);

    AvlNodeManager createAvlTree(String[] allWords);

    String findPrimeSecret(String content);
}
