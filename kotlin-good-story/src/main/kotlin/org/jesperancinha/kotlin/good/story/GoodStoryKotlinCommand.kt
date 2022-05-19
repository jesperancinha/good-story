package org.jesperancinha.kotlin.good.story

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

    override fun call(): Int = runBlocking {
        log.info(App().greeting)

        log.info(String.format("File to read is %s", textFile))
        log.info(String.format("Configured repeats are %s", massiveRepeats))


        val content =
            textFile?.let { file -> readFullContent(file) } ?: throw RuntimeException("File not configured!")

        val allUniqueWords = findAllUniqueWords(content)
        val allUniqueWordsWithCount: Map<String, Int> = findAllUniqueWordsWithCount(content)

        log.info("===> Text size is {}", content.length)

        log.info("===> All Words: {} (first 10)", allUniqueWords.subList(0, 10))
        log.info(
            "***> Processing took ${
                measureTimeMillisSave("findAllUniqueWords", algoRepeats ?: 0) {
                    GlobalScope.launch {
                        repeat(algoRepeats ?: 0) {
                            async {
                                findAllUniqueWords(content)
                            }
                        }
                    }.join()
                    log.info("Just sent {} threads", algoRepeats)
                }
            } milliseconds"
        )

        log.info("===> All Words with count: {} (first 10)", allUniqueWordsWithCount.keys.take(10))
        log.info(
            "***> Processing took ${
                measureTimeMillisSave("findAllUniqueWordsWithCount", algoRepeats ?: 0) {
                   withContext(Dispatchers.Default) {
                        (0..(algoRepeats ?: 0)).map {
                            async {
                                findAllUniqueWordsWithCount(content)
                            }
                        }.awaitAll()
                    }
                    log.info("Just sent {} threads", algoRepeats)
                }
            } milliseconds"
        )

        System.gc()
        log.info("===> Log Counter Test...")
        log.info(
            "***> Processing took ${
                measureTimeMillisSave("generalTest", massiveRepeats ?: 0) {
                    GlobalScope.launch {
                        async {
                            generalTest(massiveRepeats ?: 0)
                        }
                    }.join()
                }
            } milliseconds"
        )
        0
    }

    inline fun measureTimeMillisSave(name: String, repeats: Int, function: () -> Unit): Long {
        val totalDurationMillis = measureTimeMillis { function() }
        logFile?.let {
            FileOutputStream(logFile, true).use { objectOutputStream ->
                objectOutputStream.write(
                    String.format(
                        "| Kotlin Coroutines | %s | %d | %d | %s |\n",
                        name,
                        repeats,
                        totalDurationMillis,
                        getSystemRunningData()
                    ).toByteArray(StandardCharsets.UTF_8)
                )
                objectOutputStream.flush()
            }
        }
        return totalDurationMillis
    }

    private suspend fun findAllUniqueWordsWithCount(content: String): Map<String, Int> = makeWordsList(content)
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
        const val DEFAULT_MASSIVE_REPEATS = "10000000"
        const val DEFAULT_ALGORITHM_REPEATS = "100000"

        @DelicateCoroutinesApi
        suspend fun generalTest(repeats: Int) {
            log.info("----====>>>> Starting generalTest <<<<====----")
            val startTime = LocalDateTime.now()
            GlobalScope.launch {
                repeat(repeats) {
                    async {
                        aiVirtualThread.incrementAndGet()
                    }
                }
            }.join()
            val endTime = LocalDateTime.now()
            log.info("Imma be the main Thread")
            log.info(aiVirtualThread.get().toString())
            log.info("It took me ${Duration.between(startTime, endTime).seconds}s to finish")

            val startTimeT = LocalDateTime.now()
            val aiThread = AtomicInteger(0)
            var thread = Thread { aiThread.getAndIncrement() }
            thread.start()
            for (i in 1..99999) {
                thread = Thread { aiThread.getAndIncrement() }
                thread.start()
            }
            withContext(Dispatchers.IO) {
                thread.join()
            }
            val endTimeT = LocalDateTime.now()
            log.info("Imma be the main Thread")
            log.info(aiThread.get().toString())
            log.info("It took me ${Duration.between(startTimeT, endTimeT).seconds}s to finish")
        }

    }
}


