package org.jesperancinha.good.story

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis


@Command(
    name = "GoodStory Java Algorithms",
    mixinStandardHelpOptions = true,
    version = ["checksum 4.0"],
    description = ["Test Algorithms and measures their performance time"]
)
@DelicateCoroutinesApi
class GoodStoryKotlinCommand : Callable<Int> {

    @Option(names = ["-f", "--file"], description = ["Text.md file to be processed"], required = true)
    private var textFile: File? = null

    @Option(
        names = ["-lf", "--log-file"],
        description = ["Log.md file to record results"],
    )
    val logFile: File? = null

    @Option(
        names = ["-r", "--repeats"],
        description = ["Massive repeats"],
        defaultValue = DEFAULT_MASSIVE_REPEATS
    )
    private var massiveRepeats: Int? = null

    @Option(
        names = ["-ar", "--algo-repeats"],
        description = ["Algorithm repeats"],
        defaultValue = DEFAULT_ALGORITHM_REPEATS
    )
    private var algoRepeats: Int? = null

    @Option(
        names = ["-dump"],
        description = ["Dump log directory"],
        required = false
    )
    private var dumpDir: File? = null

    @Option(
        names = ["-computer"],
        description = ["Any data, but mostly used to say on which computer model are you running"],
        defaultValue = "Prototype",
        required = false
    )
    private var computer: String? = null

    override fun call(): Int = runBlocking {
        log.info(App().greeting)

        log.info("File to read is {}", textFile)
        log.info("Configured repeats are {}", massiveRepeats)
        log.info("Dump directory: {}", dumpDir)

        dumpDir?.let { root ->
            root.mkdirs()
            File(root, "java").mkdirs()
            File(root, "kotlin").mkdirs()
        }

        val content =
            textFile?.let { file -> readFullContent(file) } ?: throw RuntimeException("File not configured!")

        log.info("===> Text size is {}", content.length)

        performTests(
            testName = "All Words (first 10)",
            methodName = GoodStoryKotlinCommand::findAllUniqueWords.name,
            sampleTest = { findAllUniqueWords(content).subList(0, 10) },
            toTest = { findAllUniqueWords(content) },
            repeats = algoRepeats ?: 0
        )

        performTests(
            testName = "All Words with count (first 10)",
            methodName = GoodStoryKotlinCommand::findAllUniqueWordsWithCount.name,
            sampleTest = { findAllUniqueWordsWithCount(content).keys.take(10) },
            toTest = { findAllUniqueWordsWithCount(content) },
            repeats = algoRepeats ?: 0
        )

        performTests(
            testName = "Reverted Text with space complexity of O(1) and a time complexity of O(n)",
            methodName = GoodStoryKotlinCommand::revertText.name,
            sampleTest = { revertText("When they played Metallica at work") },
            toTest = { revertText(content) },
            repeats = algoRepeats ?: 0
        )

        performTests(
            testName = "Double iteration of an array of words with Space complexity of O(1) and a time complexity of O(n^2)",
            methodName = GoodStoryKotlinCommand::contentSplitIterateSubtractAndSum.name,
            sampleTest = { contentSplitIterateSubtractAndSum("You know what that is? That's a chain of responsibility pattern! ... And then he went to the Amazon") },
            toTest = { contentSplitIterateSubtractAndSum(content) },
            repeats = algoRepeats ?: 0
        )

        performGenericTests()
        0
    }

    private suspend fun <T> performTests(
        testName: String,
        methodName: String,
        sampleTest: suspend () -> T,
        toTest: suspend () -> T,
        repeats: Int
    ) {
        log.info("===> {} : {}", testName, sampleTest())
        log.info(
            "***> Processing took ${
                measureTimeMillisSave(testName, methodName, repeats) {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO) {
                            FileOutputStream(File(File(dumpDir, "kotlin"), "$methodName.csv"), true).use { oos ->
                                (0..repeats).map {
                                    startProcessAsync(oos) {
                                        toTest()
                                    }
                                }.awaitAll()
                            }
                        }
                    }.join()
                    log.info("Just sent {} threads", repeats)
                }
            } milliseconds"
        )

    }

    private suspend fun performGenericTests() {
        log.info("===> Log Control Test...")
        log.info(
            "***> Processing took ${
                measureTimeMillisSave("N/A", "controlTest", massiveRepeats ?: 0) {
                    withContext(Dispatchers.Default) {
                        controlTest(massiveRepeats ?: 0)
                    }
                }
            } milliseconds"
        )
        log.info("===> Log General Test...")
        log.info(
            "***> Processing took ${
                measureTimeMillisSave("N/A", "generalTest", massiveRepeats ?: 0) {
                    withContext(Dispatchers.Default) {
                        generalTest()
                    }
                }
            } milliseconds"
        )
    }

    private fun <T> CoroutineScope.startProcessAsync(oos: FileOutputStream?, function: suspend () -> T) =
        async {
            val start = LocalDateTime.now()
            function()
            val end = LocalDateTime.now()
            oos?.let {

                oos.write(
                    "$start,$end,${Thread.currentThread()}\n".toByteArray(StandardCharsets.UTF_8)
                )
                oos.flush()
            }

        }

    private inline fun measureTimeMillisSave(
        testName: String,
        methodName: String,
        repeats: Int,
        function: () -> Unit
    ): Long {
        val totalDurationMillis = measureTimeMillis { function() }
        logFile?.let {
            FileOutputStream(File(File(dumpDir, "kotlin"), logFile.name), true).use { objectOutputStream ->
                objectOutputStream.write(
                    "| Kotlin Coroutines | $methodName - $testName | <N/A> | <N/A> | $repeats | $totalDurationMillis | $computer |\n"
                        .toByteArray(StandardCharsets.UTF_8)
                )
                objectOutputStream.flush()
            }
        }
        return totalDurationMillis
    }


    @DelicateCoroutinesApi
    suspend fun generalTest() {
        log.info("----====>>>> Starting generalTest <<<<====----")
        val startTime = LocalDateTime.now()
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                FileOutputStream(File(File(dumpDir, "kotlin"), "generalTest.csv"), true).use { oos ->
                    (1..(massiveRepeats ?: 0)).map {
                        startProcessAsync(oos) {
                            virtualCounter.incrementAndGet()
                        }
                    }.awaitAll()
                }
            }
        }.join()
        val endTime = LocalDateTime.now()
        log.info("Imma be the main Thread")
        log.info(virtualCounter.get().toString())
        log.info("It took me {} ms to finish", Duration.between(startTime, endTime).toMillis())
    }

    /**
     * Double iteration of an array of words.
     * Result is the absolute sum of all the differences of sizes between words
     * This function follows has a quadratic big O time complexity notation of O(n^2) and a space complexity of O(1)
     */
    suspend fun contentSplitIterateSubtractAndSum(content: String): Int {
        val allWords = findAllUniqueWords(content)
        var sum = 0;
        for (element in allWords)
            for (j in allWords.size - 1 downTo 0) {
                sum += kotlin.math.abs(element.length - allWords[j].length)
            }
        return sum;
    }


    /**
     * Reverts a string using a space complexity of O(1) and a time complexity of O(n)
     *
     * @param content Content
     * @return Reverted String content
     */
    fun revertText(content: String): String {
        val charArray = content.toCharArray()
        for (i in 0 until (charArray.size / 2)) {
            val c = charArray[i]
            charArray[i] = charArray[charArray.size - i - 1]
            charArray[charArray.size - i - 1] = c
        }
        return String(charArray)
    }

    suspend fun findAllUniqueWordsWithCount(content: String): Map<String, Int> = makeWordsList(content)
        .sorted()
        .groupingBy { it }
        .eachCount()

    private suspend fun findAllUniqueWords(content: String): List<String> =
        makeWordsList(content)
            .distinct()

    private suspend fun makeWordsList(content: String): List<String> =
        content.split(" ")
            .sorted()
            .filter {
                it.filterWords()
            }

    private suspend fun String.filterWords(): Boolean = matches(Regex("[a-zA-Z]+"))

    private suspend fun readFullContent(textFile: File): String = String(withContext(Dispatchers.IO) {
        Files.readAllBytes(textFile.toPath())
    })

    companion object {

        private val log: Logger = LoggerFactory.getLogger(GoodStoryKotlinCommand::class.java)
        const val DEFAULT_MASSIVE_REPEATS = "10000"
        const val DEFAULT_ALGORITHM_REPEATS = "10000"

        @DelicateCoroutinesApi
        suspend fun controlTest(repeats: Int) {
            log.info("----====>>>> Starting controlTest <<<<====----")
            val startTimeT = LocalDateTime.now()
            val aiThread = AtomicInteger(0)
            withContext(Dispatchers.IO) {
                (1..repeats).map {
                    Thread { aiThread.getAndIncrement() }
                        .apply { start() }
                }.forEach { it.join() }
            }
            val endTimeT = LocalDateTime.now()
            log.info("Imma be the main Thread")
            log.info(aiThread.get().toString())
            log.info("It took me {} ms to finish", Duration.between(startTimeT, endTimeT).toMillis())
        }

    }
}


