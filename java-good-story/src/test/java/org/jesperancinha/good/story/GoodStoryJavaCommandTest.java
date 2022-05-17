package org.jesperancinha.good.story;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GoodStoryJavaCommandTest {

    private final GoodStoryJavaCommand goodStoryJavaCommand = new GoodStoryJavaCommand();

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
}