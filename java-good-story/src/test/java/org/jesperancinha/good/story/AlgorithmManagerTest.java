package org.jesperancinha.good.story;

import org.jesperancinha.good.story.splay.SplayNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AlgorithmManagerTest {

    private final AlgorithmInterface algorithmInterface = new AlgorithmManager();

    @Test
    public void testFindAllUniqueWordsWithCountWhenTextThenMakeCount() {
        final Map<String, Long> aa_bb_cc_aa = algorithmInterface.findAllUniqueWordsWithCount("aa bb cc aa");

        assertThat(aa_bb_cc_aa.get("aa")).isEqualTo(2);
        assertThat(aa_bb_cc_aa.get("bb")).isEqualTo(1);
        assertThat(aa_bb_cc_aa.get("cc")).isEqualTo(1);
    }

    @Test
    public void testFindAllUniqueWordsWhenTextThenMakeCount() {
        final List<String> allWords = algorithmInterface.findAllUniqueWords("aa bb cc aa");
        assertThat(allWords).containsExactlyInAnyOrder("aa", "bb", "cc");
    }

    @Test
    public void testRevertTextWhenTestThenRevert() {
        final String revertText = algorithmInterface.revertText("Lucy went to the sea and saw the beast");
        assertThat(revertText).isEqualTo("tsaeb eht was dna aes eht ot tnew ycuL");
    }

    @Test
    public void testContentSplitIterateSubtractAndSum() {
        final Integer sumResult = algorithmInterface.contentSplitIterateSubtractAndSum(
                algorithmInterface.findAllUniqueWordsArray("It's gonna be great, it's gonna be amazing, it's gonna be awesome! You're gonna love it"));
        assertThat(sumResult).isEqualTo(22);
    }

    @Test
    public void testRepetitionCount() {
        final Long repetitionCount = algorithmInterface.repetitionCount("I go to the gym all the time, I have a gymnasium and I am also Scrum master. I work 8 hours a day 5 days a week as a Scrum master and I manage the gym as well and that costs me 8 hours a day. I also have tea at 16, go out with friends, go 2 times a a week travelling, usually 5 days off per week. I know I make no sense but I am that cool. Cool");
        assertThat(repetitionCount).isEqualTo(36);
    }

    @Test
    public void testCreateAvlTree1() {
        final var nodeManager = algorithmInterface.createAvlTree(
                algorithmInterface.findAllUniqueWordsArray("c a b a b c"));
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
        final var nodeManager = algorithmInterface.createAvlTree(
                algorithmInterface.findAllUniqueWordsArray("Cevada é a bebida que aquece o coração"));
        final var parentNode = nodeManager.getParentNode();
        assertThat(parentNode.word()).isEqualTo("bebida");
        assertThat(parentNode.leftNode()).isNotNull();
        assertThat(parentNode.leftNode().word()).isEqualTo("a");
        assertThat(parentNode.rightNode()).isNotNull();
        assertThat(parentNode.rightNode().word()).isEqualTo("o");
        nodeManager.sortedTraversal();
        nodeManager.unsortedTopDownTraversal();
        nodeManager.unsortedBottomUpTraversal();
        assertThat(nodeManager.searchWord("Cevada")).isTrue();
        assertThat(nodeManager.searchWord("bebida")).isTrue();
        assertThat(nodeManager.searchWord("ginginha")).isFalse();
        assertThat(nodeManager.getNodeCount()).isEqualTo(6);
    }

    @Test
    public void testFindPrimeSecret1() {
        final var primeSecret = algorithmInterface.findPrimeSecret("It doesn't make senses. It's Charles.");
        assertThat(primeSecret).isEqualTo("I denmkes Ca");
    }

    @Test
    public void testFindPrimeSecret2() {
        final var primeSecret = algorithmInterface.findPrimeSecret("The flowers of the world and winter.");
        assertThat(primeSecret).isEqualTo("Te lw fewdwn");
    }

    @Test
    public void testCreateSplayTree1() {
        final var splayTree = algorithmInterface.createSplayTree(
                algorithmInterface.findAllUniqueWordsArray("a b c d f q r h d a d e f"));

        assertThat(splayTree).isNotNull();
        assertThat(splayTree.getRoot()).isNotNull();
        final SplayNode cNode = splayTree.find("c");
        assertThat(cNode).isNotNull();
        assertThat(cNode.word).isEqualTo("c");
    }

    @Test
    public void testCreateSplayTree2() {
        final var splayTree = algorithmInterface.createSplayTree(
                algorithmInterface.findAllUniqueWordsArray("Someone says: If you take the train, you'll be here no time! That'll only cost you 4 hours in the public transportation. I don't have that problem because I live next door, so it costs nothing to me, but I guess that's a problem to you... I say: Am I supposed to respond to that?"));

        assertThat(splayTree).isNotNull();
        assertThat(splayTree.getRoot()).isNotNull();
        final SplayNode someoneNode = splayTree.find("Someone");
        assertThat(someoneNode).isNotNull();
        assertThat(someoneNode.word).isEqualTo("Someone");
        assertThat(splayTree.maxWord()).isEqualTo("you");
        assertThat(splayTree.minWord()).isEqualTo("Am");
        assertThat(splayTree.size()).isEqualTo(31);
        splayTree.remove("Someone");
        assertThat(splayTree.find("Someone")).isNull();
    }

    @Test
    public void testQuickSort() {
        final List<String> wordsLists = algorithmInterface.quickSort(Arrays.stream(algorithmInterface.findAllUniqueWordsArray("I'll book you a meeting. Just not the one you are thinking of.")).toList());
        assertThat(wordsLists).containsExactly("Just", "a", "are", "book", "not");
    }

    @Test
    public void testJavaFlow(){
        final var text = algorithmInterface.makeTextFromWordFlow(
            algorithmInterface.findAllUniqueWords("""
                    Hi Lucy,
                                        
                    We want to thank you for the time you invested in Jumping Dogs Refuge and interviewing with us. We know there are many fantastic companies that are hiring, and we feel honored that you chose to spend time allowing us to learn more about your unique background and accomplishments.
                                        
                    I know these emails are never fun to receive, but unfortunately, we have decided to pass on your candidacy.
                    If you wish to discuss the outcome a bit, we can schedule a 10 min Zoom and I can give some high level feedback.
                                        
                    We wish you the best of luck in your job search, and again express our sincere appreciation for the time you invested in Jumping Dogs.
                                        
                    Regards,
                                        
                    Goo
                    """));

        assertThat(text).startsWith("Dogs Hi I Jumping Refuge We Zoom a about again allowing");
    }

    @Test
    public void testCreateIntersectionWordList(){
        final var intersectionLists = algorithmInterface.createIntersectionWordList("That sir is a mystery", "That gentleman is a mystery");

        assertThat(intersectionLists).isNotNull();
        assertThat(intersectionLists.get(0).next.next).isSameAs(intersectionLists.get(0).next.next);
    }

}
