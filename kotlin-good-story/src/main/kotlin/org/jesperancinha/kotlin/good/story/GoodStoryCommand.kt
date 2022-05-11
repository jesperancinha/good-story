package org.jesperancinha.kotlin.good.story

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import picocli.CommandLine.*
import java.io.File
import java.nio.file.Files
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger


@Command(
    name = "GoodStory Java Algorithms",
    mixinStandardHelpOptions = true,
    version = ["checksum 4.0"],
    description = ["Test Algorithms and measures their performance time"]
)
@DelicateCoroutinesApi
class GoodStoryCommand() : Callable<Int> {
    private val log: Logger = LoggerFactory.getLogger(GoodStoryCommand::class.java)

    @Parameters(index = "0", description = ["The file whose checksum to calculate."], defaultValue = "")
    private var file: File? = null

    @Option(names = ["-f", "--file"], description = ["Text.md file to be processed"], defaultValue = "")
    private var textFile: File? = null

    override fun call(): Int = let {
        log.info(String.format("File 0 is %s", file))
        log.info(String.format("File to read is %s", textFile))


        val content = textFile?.let { file -> readFullContent(file) } ?: throw RuntimeException("File not configured!")

        val allUniqueWords = findAllUniqueWords(content)
        val allUniqueWordsWithCount: Map<String, Int> = findAllUniqueWordsWithCount(content)

        log.info("===> Text size is {}", content.length)
        log.info("===> All Words: {}", allUniqueWords)
        log.info("===> All Words with count: {}", allUniqueWordsWithCount)

        GlobalScope.launch { generalTest() }
        0
    }


    @DelicateCoroutinesApi
    private suspend fun generalTest() {
        println(App().greeting)
        val startTime = LocalDateTime.now()
        GlobalScope.launch {
            repeat(10000000) {
                launch {
                    aiVirtualThread.incrementAndGet()
                }
            }
        }.join()
        val endTime = LocalDateTime.now()
        println("Imma be the main Thread")
        println(aiVirtualThread.get())
        println("It took me ${Duration.between(startTime, endTime).seconds}s to finish")

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
        println("Imma be the main Thread")
        println(aiThread.get())
        println("It took me ${Duration.between(startTimeT, endTimeT).seconds}s to finish")
    }


    fun findAllUniqueWordsWithCount(content: String): Map<String, Int> = makeWordsList(content)
        .sorted()
        .groupingBy { it }
        .eachCount()

    fun findAllUniqueWords(content: String): List<String> =
        makeWordsList(content)
            .distinct()

    private fun makeWordsList(content: String): List<String> =
        content.split(" ")
            .sorted()
            .filter { it.filterWords() }

    private fun String.filterWords(): Boolean = matches(Regex("[a-zA-Z]+"))

    private fun readFullContent(textFile: File): String = String(Files.readAllBytes(textFile.toPath()))
}