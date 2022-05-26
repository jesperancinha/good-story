package org.jesperancinha.good.story

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class GoodStoryKotlinCommandTest : StringSpec({

    val goodStoryCommand by lazy { GoodStoryKotlinCommand() }

    "should find words with count" {
        val allWords = goodStoryCommand.findAllUniqueWordsWithCount("a b c")

        allWords shouldBe mapOf("a" to 1, "b" to 1, "c" to 1)
    }

    "should contentSplitIterateSubtractAndSum" {
        val sum = goodStoryCommand.contentSplitIterateSubtractAndSum("ab abc abcd abcde")

        sum shouldBe 20
    }

    "should repetitionCount" {
        val repetitions =
            goodStoryCommand.repetitionCount("I go to the gym all the time, I have a gymnasium and I am also Scrum master. I work 8 hours a day 5 days a week as a Scrum master and I manage the gym as well and that costs me 8 hours a day. I also have tea at 16, go out with friends, go 2 times a a week travelling, usually 5 days off per week. I know I make no sense but I am that cool. Cool")
        repetitions shouldBe 36
    }

    "should repetitionCount 2" {
        val repetitions =
            goodStoryCommand.repetitionCount("Sitting on a table having lunch and talking about Smishing in the Bank cafeteria")
        repetitions shouldBe 0
    }

    "should repetitionCount 3" {
        val repetitions =
            goodStoryCommand.repetitionCount("The quick brown fox jumps over the lazy dog")
        repetitions shouldBe 1
    }
})
