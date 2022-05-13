package org.jesperancinha.kotlin.good.story

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.io.File
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
class GoodStoryCommand : Callable<Int> {
    private val log: Logger = LoggerFactory.getLogger(GoodStoryCommand::class.java)

    @Option(names = ["-f", "--file"], description = ["Text.md file to be processed"], required = true)
    private var textFile: File? = null

    @Option(names = ["-r", "--repeats"], description = ["Text.md file to be processed"], defaultValue = "10000000")
    private var repeats: Int? = null

    override fun call(): Int = let {

        runBlocking {
            log.info(App().greeting)

            log.info(String.format("File to read is %s", textFile))
            log.info(String.format("Configured repeats are %s", repeats))


            val content =
                textFile?.let { file -> readFullContent(file) } ?: throw RuntimeException("File not configured!")

            val allUniqueWords = findAllUniqueWords(content)
            val allUniqueWordsWithCount: Map<String, Int> = findAllUniqueWordsWithCount(content)

            log.info("===> Text size is {}", content.length)

            log.info("===> All Words: {} (first 10)", allUniqueWords.subList(0, 10))
            log.info(
                "***> Processing took ${
                    measureTimeMillis {
                        repeat(repeats ?: 0) {
                            launch {
                                findAllUniqueWords(content)
                            }
                        }
                    }
                } milliseconds"
            )

            log.info("===> All Words with count: {} (first 10)", allUniqueWordsWithCount.keys.take(10))
            log.info(
                "***> Processing took ${
                    measureTimeMillis {
                        repeat(repeats ?: 0) {
                            launch {
                                findAllUniqueWordsWithCount(content)
                            }
                        }
                    }
                } milliseconds"
            )

            log.info("===> Log Counter Test...")
            log.info(
                "***> Processing took ${
                    measureTimeMillis {
                        coroutineScope {
                            generalTest()
                        }
                    }
                } milliseconds"
            )
            0
        }
    }


    @DelicateCoroutinesApi
    private suspend fun generalTest() {
        val startTime = LocalDateTime.now()
        GlobalScope.launch {
            repeat(repeats ?: 0) {
                launch {
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
            .filter { it.filterWords() }

    private suspend fun String.filterWords(): Boolean = matches(Regex("[a-zA-Z]+"))

    private suspend fun readFullContent(textFile: File): String = String(Files.readAllBytes(textFile.toPath()))
}