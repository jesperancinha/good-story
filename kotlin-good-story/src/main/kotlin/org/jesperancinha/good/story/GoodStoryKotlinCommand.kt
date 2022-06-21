package org.jesperancinha.good.story

import com.opencsv.CSVWriter.DEFAULT_SEPARATOR
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import com.opencsv.bean.StatefulBeanToCsv
import com.opencsv.bean.StatefulBeanToCsvBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.jesperancinha.good.story.intersection.InterNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer
import java.util.function.Supplier
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

    private var dumpWriter: FileWriter? = null

    private val functionReadings: MutableList<FunctionReading> = ArrayList()

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

        val dumpFile = File(dumpDir, "dump.csv")
        if (dumpFile.exists()) {
            val fileReader = withContext(IO) {
                FileReader(dumpFile)
            }
            val csvReader: CsvToBean<FunctionReading> = CsvToBeanBuilder<FunctionReading>(fileReader)
                .withType(FunctionReading::class.java)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build()
            val functionReadingList: List<FunctionReading> = csvReader.parse()
            functionReadings.addAll(functionReadingList)
        }

        dumpWriter = withContext(IO) {
            FileWriter(dumpFile)
        }

        val algorithmManager = AlgorithmManager()

        val content =
            textFile?.let { file -> readFullContent(file) } ?: throw RuntimeException("File not configured!")
        val allWords = algorithmManager.findAllUniqueWordsArray(content)

        log.info("===> Text size is {}", content.length)


        performTest(
            testName = "wait0Nanos",
            methodName = AlgorithmManager::findAllUniqueWords.name,
            sampleTest = { algorithmManager.wait0Nanos() },
            toTest = { algorithmManager.wait0Nanos() },
            repeats = 2
        )

        performTest(
            testName = "All Unique Words",
            methodName = AlgorithmManager::findAllUniqueWords.name,
            sampleTest = { algorithmManager.findAllUniqueWords(content).subList(0, 10) },
            toTest = { algorithmManager.findAllUniqueWords(content) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "All Words with count",
            methodName = AlgorithmManager::findAllUniqueWordsWithCount.name,
            sampleTest = { algorithmManager.findAllUniqueWordsWithCount(content).keys.take(10) },
            toTest = { algorithmManager.findAllUniqueWordsWithCount(content) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "Reverted Text",
            methodName = AlgorithmManager::revertText.name,
            timeComplexity = "O(n)",
            spaceComplexity = "O(1)",
            sampleTest = { algorithmManager.revertText("When they played Metallica at work") },
            toTest = { algorithmManager.revertText(content) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "Double iteration of an array of words",
            timeComplexity = "O(n^2)",
            spaceComplexity = "O(1)",
            methodName = AlgorithmManager::contentSplitIterateSubtractAndSum.name,
            sampleTest = { algorithmManager.contentSplitIterateSubtractAndSum(algorithmManager.findAllUniqueWordsArray("You know what that is? That's a chain of responsibility pattern! ... And then he went to the Amazon")) },
            toTest = { algorithmManager.contentSplitIterateSubtractAndSum(allWords) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "Repetition count",
            timeComplexity = "O(n^2)",
            spaceComplexity = "O(n)",
            methodName = AlgorithmManager::repetitionCount.name,
            sampleTest = { algorithmManager.repetitionCount("Sitting on a table having lunch and talking about Smishing in the Bank cafeteria.") },
            toTest = { algorithmManager.repetitionCount(content) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "Create AVL Tree",
            timeComplexity = "O(log n)",
            spaceComplexity = "O(n)",
            methodName = AlgorithmManager::createAvlTree.name,
            sampleTest = { algorithmManager.createAvlTree(algorithmManager.findAllUniqueWordsArray("I sat with Mr.Ek and he said something about the toilet paper, but I don't know exactly what it was. Strange.")) },
            toTest = { algorithmManager.createAvlTree(allWords) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "Secret word in Sieve of Eratosthenes",
            methodName = AlgorithmManager::findPrimeSecret.name,
            timeComplexity = "O(n * log(log n))",
            spaceComplexity = "O(n)",
            sampleTest = { algorithmManager.findPrimeSecret("We should always test the functionality product needs but they said otherwise.") },
            toTest = { algorithmManager.findPrimeSecret(content) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "Create Splay Tree",
            methodName = AlgorithmManager::createSplayTree.name,
            timeComplexity = "O(log n)",
            spaceComplexity = "O(n)",
            sampleTest = {
                algorithmManager.createSplayTree(
                    algorithmManager.findAllUniqueWordsArray("And then the Wolf said refrain this, refrain that.")
                )
            },
            toTest = { algorithmManager.createSplayTree(allWords) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "Quick sort",
            methodName = AlgorithmManager::quickSort.name,
            timeComplexity = "O(n * log n)",
            spaceComplexity = "O(log n)",
            sampleTest = {
                algorithmManager.quickSort(
                    algorithmManager.findAllUniqueWords("He never took things as a suggestion, advice, or ideas.")
                )
            },
            toTest = { algorithmManager.quickSort(allWords.toList()) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "Make text from word Flow",
            methodName = AlgorithmManager::makeTextFromWordFlow.name,
            timeComplexity = "n/a",
            spaceComplexity = "n/a",
            sampleTest = {
                algorithmManager.makeTextFromWordFlow(
                    algorithmManager.findAllUniqueWords("I think they use a strange trick. They just ask the Big O question.")
                )
            },
            toTest = { algorithmManager.makeTextFromWordFlow(allWords.toList()) },
            repeats = algoRepeats ?: 0
        )

        performTest(
            testName = "Intersection Text Algorithm",
            methodName = AlgorithmManager::createIntersectionWordList.name,
            timeComplexity = "O(n)",
            spaceComplexity = "O(n)",
            sampleTest = {
                algorithmManager.createIntersectionWordList(
                    "It was great to do the monitoring setup and implementation",
                    "Time to think about the logging setup and implementation"
                )
            },
            toTest = {
                algorithmManager.createIntersectionWordList(
                    content.substring(0, 500),
                    content.substring(0, 500)
                )
            },
            repeats = algoRepeats ?: 0
        )

        performGenericTests()
        tearDownAlgorithmManager()

        0
    }

    private suspend fun tearDownAlgorithmManager() {
        val sbc: StatefulBeanToCsv<FunctionReading> = StatefulBeanToCsvBuilder<FunctionReading>(dumpWriter)
            .withSeparator(DEFAULT_SEPARATOR)
            .build()
        sbc.write(functionReadings)
        withContext(IO) {
            dumpWriter?.close()
        }

        dumpDir?.let { root ->
            File(root, "kotlin").listFiles(FileFilter { it.name.endsWith(".csv") && !it.name.endsWith("-ms.csv") })
                ?.forEach { source ->
                    FileOutputStream(File(File(root, "kotlin"), "${source.name}-ms.csv"), true)
                        .use { oos ->
                            val pairList = source.readLines()
                                .map {
                                    val (start, end) = it.split(",")
                                    LocalDateTime.parse(start) to LocalDateTime.parse(end)
                                }
                                .sortedBy { it.first }
                            val first = pairList.first().first
                            val last = pairList.last().first

                            val delta = Duration.between(first, last).toMillis() / 100
                            (1..100).map { n ->
                                val size = pairList.filter {
                                    first.plusNanos(n * delta * 1000) > it.first && first.plusNanos(n * delta * 1000) < it.second
                                }.size
                                oos.write("$n,$size\n".toByteArray(StandardCharsets.UTF_8))
                            }
                            oos.flush()
                        }

                }
        }

        withContext(IO) {
            FileOutputStream(logFile).use { oos ->
                oos.write(
                    "| Time | Method | Time Complexity | Space Complexity | Repetitions | Java Duration | Kotlin Duration | Machine |\n".toByteArray(
                        StandardCharsets.UTF_8
                    )
                )
                oos.write("|---|---|---|---|---|---|---|---|\n".toByteArray(StandardCharsets.UTF_8))
                functionReadings.forEach(Consumer { fr: FunctionReading ->
                    try {
                        oos.write(
                            String.format(
                                "| %s | %s | %s | %s | %d | %d | %d | %s |\n",
                                LocalDateTime.now(),
                                fr.method,
                                fr.timeComplexity,
                                fr.spaceComplexity,
                                fr.repetition,
                                fr.javaDuration,
                                fr.kotlinDuration,
                                fr.machine
                            ).toByteArray(StandardCharsets.UTF_8)
                        )
                        oos.flush()
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    }
                })
            }
        }
    }

    private suspend fun <T> performTest(
        testName: String,
        methodName: String,
        timeComplexity: String = "n/a",
        spaceComplexity: String = "n/a",
        sampleTest: suspend () -> T,
        toTest: suspend () -> T,
        repeats: Int
    ) {
        log.info("===> {} : {}", testName, sampleTest())
        log.info(
            "***> Processing took ${
                measureTimeMillisSave(testName, methodName, timeComplexity, spaceComplexity, repeats = repeats) {
                    withContext(IO) {
                        FileOutputStream(File(File(dumpDir, "kotlin"), "$methodName.csv"), true).use { oos ->
                            (0..repeats).map {
                                startProcessAsync(oos) {
                                    toTest()
                                }
                            }.awaitAll()
                        }
                    }
                    log.info("Just sent {} threads", repeats)
                }
            } milliseconds"
        )
    }

    private suspend fun performGenericTests() {
        log.info("===> Log Control Test...")
        log.info(
            "***> Processing took ${
                measureTimeMillisSave("N/A", "controlTest", repeats = massiveRepeats ?: 0) {
                    withContext(Dispatchers.Default) {
                        controlTest(massiveRepeats ?: 0)
                    }
                }
            } milliseconds"
        )
        log.info("===> Log General Test...")
        log.info(
            "***> Processing took ${
                measureTimeMillisSave("N/A", "generalTest", repeats = massiveRepeats ?: 0) {
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
        timeComplexity: String = "n/a",
        spaceComplexity: String = "n/a",
        repeats: Int,
        function: () -> Unit
    ): Long {
        val totalDurationMillis = measureTimeMillis { function() }
        logFile?.let {
            FileOutputStream(File(File(dumpDir, "kotlin"), logFile.name), true).use { objectOutputStream ->
                objectOutputStream.write(
                    "| Kotlin Coroutines | $methodName - $testName | $timeComplexity | $spaceComplexity | $repeats | $totalDurationMillis | $computer |\n"
                        .toByteArray(StandardCharsets.UTF_8)
                )

                val functionReading = FunctionReading(
                    "$methodName - $testName",
                    timeComplexity,
                    spaceComplexity,
                    repeats.toLong(),
                    -1L,
                    totalDurationMillis,
                    computer
                )
                val destination = functionReadings.stream()
                    .filter { fr: FunctionReading -> fr.method == functionReading.method }
                    .findFirst().orElse(null)

                if (destination == null) {
                    functionReadings.add(functionReading)
                } else {
                    destination.kotlinDuration = totalDurationMillis
                }
                objectOutputStream.flush()
            }
        }
        return totalDurationMillis
    }


    @DelicateCoroutinesApi
    suspend fun generalTest() {
        log.info("----====>>>> Starting generalTest <<<<====----")
        val startTime = LocalDateTime.now()
        withContext(IO) {
            FileOutputStream(File(File(dumpDir, "kotlin"), "generalTest.csv"), true).use { oos ->
                (1..(massiveRepeats ?: 0)).map {
                    startProcessAsync(oos) {
                        virtualCounter.incrementAndGet()
                    }
                }.awaitAll()
            }
        }
        val endTime = LocalDateTime.now()
        log.info("Imma be the main Thread")
        log.info(virtualCounter.get().toString())
        log.info("It took me {} ms to finish", Duration.between(startTime, endTime).toMillis())
    }

    private suspend fun readFullContent(textFile: File): String = String(withContext(IO) {
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
            withContext(IO) {
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


