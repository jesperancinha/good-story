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
})
