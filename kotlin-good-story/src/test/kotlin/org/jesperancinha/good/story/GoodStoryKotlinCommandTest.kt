package org.jesperancinha.good.story

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.DelicateCoroutinesApi
import org.junit.jupiter.api.Test

@DelicateCoroutinesApi
class GoodStoryKotlinCommandTest : StringSpec({

    val algorithmManager by lazy { AlgorithmManager() }

    "should find words with count" {
        val allWords = algorithmManager.findAllUniqueWordsWithCount("a b c")

        allWords shouldBe mapOf("a" to 1, "b" to 1, "c" to 1)
    }

    "should contentSplitIterateSubtractAndSum" {
        val sum = algorithmManager.contentSplitIterateSubtractAndSum(algorithmManager.makeWordsArrayList("ab abc abcd abcde"))

        sum shouldBe 20
    }

    "should repetitionCount" {
        val repetitions =
            algorithmManager.repetitionCount("I go to the gym all the time, I have a gymnasium and I am also Scrum master. I work 8 hours a day 5 days a week as a Scrum master and I manage the gym as well and that costs me 8 hours a day. I also have tea at 16, go out with friends, go 2 times a a week travelling, usually 5 days off per week. I know I make no sense but I am that cool. Cool")
        repetitions shouldBe 36
    }

    "should repetitionCount 2" {
        val repetitions =
            algorithmManager.repetitionCount("Sitting on a table having lunch and talking about Smishing in the Bank cafeteria")
        repetitions shouldBe 0
    }

    "should repetitionCount 3" {
        val repetitions =
            algorithmManager.repetitionCount("The quick brown fox jumps over the lazy dog")
        repetitions shouldBe 1
    }

    "should create AVL tree test 1" {
        val nodeManager = algorithmManager.createAvlTree(algorithmManager.makeWordsArrayList("c a b a b c"))
        val parentNode = nodeManager.parentNode
        parentNode.apply {
            shouldNotBeNull()
            word shouldBe "b"
            leftNode.apply {
                shouldNotBeNull()
                word shouldBe "a"
            }
            rightNode.apply {
                shouldNotBeNull()
                word shouldBe "c"
            }
        }
        nodeManager.apply {
            sortedTraversal()
            unsortedTopDownTraversal()
            unsortedBottomUpTraversal()

            searchWord("a").shouldBeTrue()
            searchWord("d").shouldBeFalse()
            searchWord("c").shouldBeTrue()
            nodeCount shouldBe 3
        }
    }

    "should create AVL tree test 2" {
        val nodeManager =
            algorithmManager.createAvlTree(algorithmManager.makeWordsArrayList("When I went up the stairs to return my computer, the man with guilt and the weight of a life filled with shamelessness and regretful decisions on his shoulders didn't even know who I was."))
        val parentNode = nodeManager.parentNode
        parentNode.apply {
            shouldNotBeNull()
            word shouldBe "regretful"
            leftNode.apply {
                shouldNotBeNull()
                word shouldBe "guilt"
            }
            rightNode.apply {
                shouldNotBeNull()
                word shouldBe "stairs"
            }
        }
        nodeManager.apply {
            sortedTraversal()
            unsortedTopDownTraversal()
            unsortedBottomUpTraversal()

            searchWord("a").shouldBeTrue()
            searchWord("d").shouldBeFalse()
            searchWord("c").shouldBeFalse()
            searchWord("shamelessness").shouldBeTrue()
            nodeCount shouldBe 27
        }
    }

    "should find secret word 1" {
        val primeSecret: String = algorithmManager.findPrimeSecret("It doesn't make senses. It's Charles.")
        primeSecret shouldBe "I denmkes Ca"
    }

    "should find secret word 2" {
        val primeSecret: String =
            algorithmManager.findPrimeSecret("The flowers of the world and winter.")
        primeSecret shouldBe "Te lw fewdwn"
    }

    "should create splay tree" {
        val splayTree = algorithmManager.createSplayTree(
            algorithmManager.makeWordsArrayList("Here is van-guardian idea. How about we stop talking about being positive over negative, end this mystical idea of positive over negative and get our act together and solve the problems in our lives? How about that huh? Isn't that also positive"))


        splayTree.shouldNotBeNull()
        splayTree.root.apply {
            shouldNotBeNull()
            word shouldBe "we"
            left.apply {
                shouldNotBeNull()
                word shouldBe "together"
            }
            right.apply {
                shouldBeNull()
            }
        }
        splayTree.find("idea").shouldNotBeNull()
        splayTree.find("technodiktator").shouldBeNull()
    }
})
