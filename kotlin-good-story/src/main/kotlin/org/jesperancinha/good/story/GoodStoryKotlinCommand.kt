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

        val allUniqueWords = findAllUniqueWords(content)
        val allUniqueWordsWithCount: Map<String, Int> = findAllUniqueWordsWithCount(content)

        log.info("===> Text size is {}", content.length)

        log.info("===> All Words: {} (first 10)", allUniqueWords.subList(0, 10))
        log.info(
            "***> Processing took ${
                measureTimeMillisSave(GoodStoryKotlinCommand::findAllUniqueWords.name, algoRepeats ?: 0) {
                    GlobalScope.launch {
                        (0..(algoRepeats ?: 0)).map {
                            startProcessAsync(GoodStoryKotlinCommand::findAllUniqueWords.name) {
                                findAllUniqueWords(content)
                            }
                        }.awaitAll()
                    }.join()
                    log.info("Just sent {} threads", algoRepeats)
                }
            } milliseconds"
        )

        log.info("===> All Words with count: {} (first 10)", allUniqueWordsWithCount.keys.take(10))
        log.info(
            "***> Processing took ${
                measureTimeMillisSave(GoodStoryKotlinCommand::findAllUniqueWordsWithCount.name, algoRepeats ?: 0) {
                    withContext(Dispatchers.Default) {
                        (0..(algoRepeats ?: 0)).map {
                            startProcessAsync(GoodStoryKotlinCommand::findAllUniqueWordsWithCount.name) {
                                findAllUniqueWordsWithCount(content)
                            }
                        }.awaitAll()
                    }
                    log.info("Just sent {} threads", algoRepeats)
                }
            } milliseconds"
        )

        log.info("===> Log Control Test...")
        log.info(
            "***> Processing took ${
                measureTimeMillisSave("controlTest", massiveRepeats ?: 0) {
                    withContext(Dispatchers.Default) {
                        controlTest(massiveRepeats ?: 0)
                    }
                }
            } milliseconds"
        )
        log.info("===> Log General Test...")
        log.info(
            "***> Processing took ${
                measureTimeMillisSave("generalTest", massiveRepeats ?: 0) {
                    withContext(Dispatchers.Default) {
                        generalTest()
                    }
                }
            } milliseconds"
        )
        0
    }

    private fun <T> CoroutineScope.startProcessAsync(name: String, function: suspend () -> T) = async {
        val start = LocalDateTime.now()
        function()
        val end = LocalDateTime.now()
        dumpDir?.let {
            FileOutputStream(File(File(it, "kotlin"), "$name.csv"), true).use { oos ->
                oos.write(
                    "$start,$end\n".toByteArray(StandardCharsets.UTF_8)
                )
                oos.flush()
            }
        }

    }

    private inline fun measureTimeMillisSave(name: String, repeats: Int, function: () -> Unit): Long {
        val totalDurationMillis = measureTimeMillis { function() }
        logFile?.let {
            FileOutputStream(logFile, true).use { objectOutputStream ->
                objectOutputStream.write(
                    "| Kotlin Coroutines | $name | $repeats | $totalDurationMillis | $computer |\n"
                        .toByteArray(StandardCharsets.UTF_8)
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

    @DelicateCoroutinesApi
    suspend fun generalTest() {
        log.info("----====>>>> Starting generalTest <<<<====----")
        val startTime = LocalDateTime.now()
        GlobalScope.launch {
            (0..(massiveRepeats ?: 0)).map {
                startProcessAsync(GoodStoryKotlinCommand::generalTest.name) {
                    virtualCounter.incrementAndGet()
                }
            }.awaitAll()
        }.join()
        val endTime = LocalDateTime.now()
        log.info("Imma be the main Thread")
        log.info(virtualCounter.get().toString())
        log.info("It took me {} ms to finish", Duration.between(startTime, endTime).toMillis())
    }


    companion object {

        private val log: Logger = LoggerFactory.getLogger(GoodStoryKotlinCommand::class.java)
        const val DEFAULT_MASSIVE_REPEATS = "10000"
        const val DEFAULT_ALGORITHM_REPEATS = "10000"

        @DelicateCoroutinesApi
        suspend fun controlTest(repeats: Int) {
            log.info("----====>>>> Starting controlTest <<<<====----")
            val startTimeT = LocalDateTime.now()
            val aiThread = AtomicInteger(0)
            var thread = Thread { aiThread.getAndIncrement() }
            thread.start()
            for (i in 0..repeats) {
                thread = Thread { aiThread.getAndIncrement() }
                thread.start()
            }
            withContext(Dispatchers.IO) {
                thread.join()
            }
            val endTimeT = LocalDateTime.now()
            log.info("Imma be the main Thread")
            log.info(aiThread.get().toString())
            log.info("It took me {} ms to finish", Duration.between(startTimeT, endTimeT).toMillis())
        }

    }
}


