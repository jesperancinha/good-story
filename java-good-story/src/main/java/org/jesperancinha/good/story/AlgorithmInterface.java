package org.jesperancinha.good.story;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface AlgorithmInterface {
    Integer contentSplitIterateSubtractAndSum(String content);

    Long repetitionCount(String content);

    String revertText(String content);

    Map<String, Long> findAllUniqueWordsWithCount(String content);

    List<String> findAllUniqueWords(String content);

    Stream<String> makeWordsList(String content);

    AvlNode createAvlTree(String content);
}
