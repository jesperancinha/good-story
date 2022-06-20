package org.jesperancinha.good.story;

import org.jesperancinha.good.story.avl.AvlTree;
import org.jesperancinha.good.story.intersection.InterNode;
import org.jesperancinha.good.story.splay.SplayTree;

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

    AvlTree createAvlTree(String[] allWords);

    String findPrimeSecret(String content);

    SplayTree createSplayTree(String[] allWords);

    List<String> quickSort(List<String> allWords);

    String makeTextFromWordFlow(List<String> words);

    List<InterNode> createIntersectionWordList(String sentenceLeft, String sentenceRight);

    String saveWords(List<String> words);

   String saveWordsParking(List<String> words);
}
