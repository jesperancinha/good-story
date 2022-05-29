package org.jesperancinha.good.story;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GoodStoryJavaCommandTest {

    private final AlgorithmInterface goodStoryJavaCommand = new AlgorithmManager();

    @Test
    public void testFindAllUniqueWordsWithCountWhenTextThenMakeCount() {
        final Map<String, Long> aa_bb_cc_aa = goodStoryJavaCommand.findAllUniqueWordsWithCount("aa bb cc aa");

        assertThat(aa_bb_cc_aa.get("aa")).isEqualTo(2);
        assertThat(aa_bb_cc_aa.get("bb")).isEqualTo(1);
        assertThat(aa_bb_cc_aa.get("cc")).isEqualTo(1);
    }

    @Test
    public void testFindAllUniqueWordsWhenTextThenMakeCount() {
        final List<String> allWords = goodStoryJavaCommand.findAllUniqueWords("aa bb cc aa");
        assertThat(allWords).containsExactlyInAnyOrder("aa", "bb", "cc");
    }

    @Test
    public void testRevertTextWhenTestThenRevert() {
        final String revertText = goodStoryJavaCommand.revertText("Lucy went to the sea and saw the beast");
        assertThat(revertText).isEqualTo("tsaeb eht was dna aes eht ot tnew ycuL");
    }

    @Test
    public void testContentSplitIterateSubtractAndSum() {
        final Integer sumResult = goodStoryJavaCommand.contentSplitIterateSubtractAndSum("It's gonna be great, it's gonna be amazing, it's gonna be awesome! You're gonna love it");
        assertThat(sumResult).isEqualTo(22);
    }

    @Test
    public void testRepetitionCount() {
        final Long repetitionCount = goodStoryJavaCommand.repetitionCount("I go to the gym all the time, I have a gymnasium and I am also Scrum master. I work 8 hours a day 5 days a week as a Scrum master and I manage the gym as well and that costs me 8 hours a day. I also have tea at 16, go out with friends, go 2 times a a week travelling, usually 5 days off per week. I know I make no sense but I am that cool. Cool");
        assertThat(repetitionCount).isEqualTo(36);
    }

    @Test
    public void testCreateAvlTree1() {
        final var nodeManager = goodStoryJavaCommand.createAvlTree("c a b a b c");
        final var parentNode = nodeManager.getParentNode();
        assertThat(parentNode.word()).isEqualTo("b");
        assertThat(parentNode.leftNode()).isNotNull();
        assertThat(parentNode.leftNode().word()).isEqualTo("a");
        assertThat(parentNode.rightNode()).isNotNull();
        assertThat(parentNode.rightNode().word()).isEqualTo("c");
        nodeManager.sortedTraversal();
        nodeManager.unsortedTopDownTraversal();
        nodeManager.unsortedBottomUpTraversal();
        assertThat(nodeManager.searchWord("a")).isTrue();
        assertThat(nodeManager.searchWord("d")).isFalse();
        assertThat(nodeManager.searchWord("c")).isTrue();
        assertThat(nodeManager.getNodeCount()).isEqualTo(3);

    }
    @Test
    public void testCreateAvlTree2() {
        final var nodeManager = goodStoryJavaCommand.createAvlTree("Brasa é a bebida que aquece o coração");
        final var parentNode = nodeManager.getParentNode();
        assertThat(parentNode.word()).isEqualTo("bebida");
        assertThat(parentNode.leftNode()).isNotNull();
        assertThat(parentNode.leftNode().word()).isEqualTo("a");
        assertThat(parentNode.rightNode()).isNotNull();
        assertThat(parentNode.rightNode().word()).isEqualTo("o");
        nodeManager.sortedTraversal();
        nodeManager.unsortedTopDownTraversal();
        nodeManager.unsortedBottomUpTraversal();
        assertThat(nodeManager.searchWord("Brasa")).isTrue();
        assertThat(nodeManager.searchWord("bebida")).isTrue();
        assertThat(nodeManager.searchWord("ginginha")).isFalse();
        assertThat(nodeManager.getNodeCount()).isEqualTo(6);
    }
}
