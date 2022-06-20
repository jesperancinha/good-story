package org.jesperancinha.good.story;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static java.lang.Thread.startVirtualThread;
import static java.time.Duration.between;
import static java.util.stream.IntStream.range;

/**
 * Created by jofisaes on 10/05/2022
 */
@Command(name = "GoodStory Java Algorithms",
        mixinStandardHelpOptions = true,
        version = "checksum 4.0",
        description = "Test Algorithms and measures their performance time")
class GoodStoryJavaCommand implements Callable<Integer> {

    @Option(names = {"-f", "--file"},
            description = "Text.md file to be processed",
            required = true)
    private final File textFile;

    {
        try {
            textFile = File.createTempFile("/tmp", "text.md");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Option(names = {"-lf", "--log-file"},
            description = "Log.md file to record results",
            required = true)
    private final File logFile;

    {
        try {
            logFile = File.createTempFile("tmp", "log.md");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Option(names = {"-r", "--repeats"},
            description = {"Massive repeats"},
            defaultValue = DEFAULT_MASSIVE_REPEATS)
    private final Integer massiveRepeats = 0;

    @Option(names = {"-ar", "--algo-repeats"},
            description = {"Algorithm repeats"},
            defaultValue = DEFAULT_ALGORITHM_REPEATS)
    private final Integer algoRepeats = 0;


    @Option(
            names = {"-dump"},
            description = {"Dump log directory"}
    )
    private final File dumpDir;

    {
        try {
            dumpDir = File.createTempFile("tmp", "dump");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Option(
            names = {"-computer"},
            description = {"Any data, but mostly used to say on which computer model are you running"},
            defaultValue = "Prototype"
    )
    private String computer = "";

    private FileWriter dumpWriter;

    private List<FunctionReading> functionReadings = new ArrayList<>();

    @Override
    public Integer call() throws Exception {
        log.info("Welcome to the Java Project Loom Test!");
        log.info(String.format("File to read is %s", textFile));

        final AlgorithmInterface algorithmManager = setupAlgorithManager();

        final var content = readFullContent();
        final var allWords = algorithmManager.findAllUniqueWords(content).toArray(new String[0]);

        log.info("===> Text size is {}", content.length());

        performTest(
                "Write to 1 file - No Parking - Virtual Thread",
                "saveWordsNio",
                "n/a", "n/a",
                () -> algorithmManager.saveWords(algorithmManager.findAllUniqueWords("I had problem B, but he understood problem A, then he created problem C out of problem A, said problem C was mine and presented me with a solution to problem C. My original problem was never solved and he ended up getting a promotion.")),
                () -> algorithmManager.saveWords(algorithmManager.findAllUniqueWords(content)), 2);

        performTest(
                "Write to 1 file - Parking - Virtual Thread",
                "saveWordsNio",
                "n/a", "n/a",
                () -> algorithmManager.saveWordsParking(algorithmManager.findAllUniqueWords("When I make meetings I always make sure they are technical, but tell me what did that with you")),
                () -> algorithmManager.saveWordsParking(algorithmManager.findAllUniqueWords(content)), 2);

        performTest(
                "All Unique Words",
                "findAllUniqueWords",
                "n/a", "n/a",
                () -> String.join(".",
                        algorithmManager.findAllUniqueWords(content).subList(0, 10)),
                () -> algorithmManager.findAllUniqueWords(content), algoRepeats);

        performTest(
                "All Words with count",
                "findAllUniqueWordsWithCount",
                "n/a", "n/a",
                () -> String.join(".",
                        algorithmManager.findAllUniqueWordsWithCount(content).entrySet().stream().map((entry) ->
                                String.format("%s to %d", entry.getKey(), entry.getValue())).toList().subList(0, 10)),
                () -> algorithmManager.findAllUniqueWordsWithCount(content), algoRepeats);

        performTest(
                "Reverted Text",
                "revertText",
                "O(n)", "O(1)",
                () -> algorithmManager.revertText("Lucy meets Menna and the Fish"),
                () -> algorithmManager.revertText(content), algoRepeats);

        performTest(
                "Double iteration of an array of words",
                "contentSplitIterateSubtractAndSum",
                "O(n^2)", "O(1)",
                () -> algorithmManager.contentSplitIterateSubtractAndSum(
                        algorithmManager.findAllUniqueWordsArray("Oh there you are Mr. Fallout!")),
                () -> algorithmManager.contentSplitIterateSubtractAndSum(allWords), algoRepeats);

        performTest(
                "Repetition count",
                "repetitionCount",
                "O(n^2)", "O(n)",
                () -> algorithmManager.repetitionCount("I know he let the dog bark and he was using slack to support a production crisis, but that doesn't mean he can't perform an interview at the same time right?"),
                () -> algorithmManager.repetitionCount(content), algoRepeats);

        performTest(
                "Create AVL Tree",
                "createAvlTree",
                "O(log n)", "O(n)",
                () -> algorithmManager.createAvlTree(
                        algorithmManager.findAllUniqueWordsArray("Let me get back you. Let's chat in 15 minutes")),
                () -> algorithmManager.createAvlTree(allWords), algoRepeats);

        performTest(
                "Secret word in Sieve of Eratosthenes",
                "findPrimeSecret",
                "O(n * log(log n))", "O(n)",
                () -> algorithmManager.findPrimeSecret("A trainer should say motivating and encouraging words such as \"I believe that you can do it\", I believe my Lord. That's not exactly what he said is it?"),
                () -> algorithmManager.findPrimeSecret(content), algoRepeats);

        performTest(
                "Create Splay Tree",
                "createSplayTree",
                "O(log n)", "O(n)",
                () -> algorithmManager.createSplayTree(algorithmManager.findAllUniqueWordsArray("My concern is that I need tools. So many tools. So little time!")),
                () -> algorithmManager.createSplayTree(allWords), algoRepeats);

        performTest(
                "Quick sort",
                "quickSort",
                "O(n * log n)", "O(log n)",
                () -> algorithmManager.quickSort(List.of(algorithmManager.findAllUniqueWordsArray("If you ask me I think the great Inflexion Point is coming. Like winter"))),
                () -> algorithmManager.quickSort(List.of(allWords)), algoRepeats);

        performTest(
                "Make text from word Flow",
                "makeTextFromWordFlow",
                "n/a", "n/a",
                () -> algorithmManager.makeTextFromWordFlow(List.of(algorithmManager.findAllUniqueWordsArray("As I saw the lift skipping the 13th floor I realised that there was no mission. I had to pay for my hotel"))),
                () -> algorithmManager.makeTextFromWordFlow(List.of(allWords)), algoRepeats);

        performTest(
                "Intersection Text Algorithm",
                "createIntersectionWordList",
                "O(n)", "O(n)",
                () -> algorithmManager.createIntersectionWordList("Nobody gave me a business card", "Nobody told me about a business card"),
                () -> algorithmManager.createIntersectionWordList(content.substring(0, 500), content.substring(0, 500)), algoRepeats);

        performGenericTests();

        tearDownAlgorithmManager();
        return 0;
    }

    private void tearDownAlgorithmManager() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        var sbc = new StatefulBeanToCsvBuilder<FunctionReading>(dumpWriter)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();
        sbc.write(functionReadings);
        dumpWriter.close();

        try (FileOutputStream oos = new FileOutputStream(logFile)) {
            oos.write("| Time | Method | Time Complexity | Space Complexity | Repetitions | Java Duration | Kotlin Duration | Machine |\n".getBytes(StandardCharsets.UTF_8));
            oos.write("|---|---|---|---|---|---|---|---|\n".getBytes(StandardCharsets.UTF_8));
            functionReadings.forEach(fr -> {
                try {
                    oos.write(
                            String.format("| %s | %s | %s | %s | %d | %d | %d | %s |\n",
                                    LocalDateTime.now(), fr.getMethod(), fr.getTimeComplexity(), fr.getSpaceComplexity(), fr.getRepetition(),
                                    fr.getJavaDuration(), fr.getKotlinDuration(), fr.getMachine()).getBytes(StandardCharsets.UTF_8));
                    oos.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private AlgorithmInterface setupAlgorithManager() throws IOException {
        if (Objects.nonNull(dumpDir)) {
            dumpDir.mkdirs();
            new File(dumpDir, "java").mkdirs();
            new File(dumpDir, "kotlin").mkdirs();
        }

        final File dumpFile = new File(dumpDir, "dump.csv");
        if (dumpFile.exists()) {
            var fileReader = new FileReader(dumpFile);
            var csvReader = new CsvToBeanBuilder<FunctionReading>(fileReader)
                    .withType(FunctionReading.class)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();
            final List<FunctionReading> functionReadingList = csvReader.parse();
            functionReadings.addAll(functionReadingList);
        }

        dumpWriter = new FileWriter(dumpFile);
        return new AlgorithmManager();
    }

    private void performGenericTests() throws IOException {
        log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
            try {
                controlTest();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "N/A", "controlTest", "n/a", "n/a", massiveRepeats));

        try (FileOutputStream generalTestOos = new FileOutputStream(new File(new File(dumpDir, "java"), "generalTest.csv"), true)) {
            log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
                try {
                    generalTest(generalTestOos);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }, "N/A", "generalTest", "n/a", "n/a", massiveRepeats));

        }
    }

    private <T> void performTest(String testName, String methodName, String timeComplexity, String spaceComplexity, Supplier<T> sampleTest, Runnable toTest, int repeats) {
        try (FileOutputStream oos = new FileOutputStream(new File(new File(dumpDir, "java"), String.format("%s.csv", methodName)), true)) {
            log.info("===> Starting test: {}: {}", testName, sampleTest.get());
            log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
                final List<Thread> threadStream = range(0, repeats).mapToObj(i -> startProcessAsync(toTest, oos)).toList();
                log.info("---> Just sent {} threads", repeats);
                threadStream
                        .forEach(thread -> {
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }, testName, methodName, timeComplexity, spaceComplexity, repeats));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Thread startProcessAsync(Runnable runnable, FileOutputStream oos) {
        Runnable threadRunnable = () -> {
            var start = LocalDateTime.now();
            runnable.run();
            var end = LocalDateTime.now();
            try {
                oos.write(
                        String.format("%s,%s,%s\n", start, end, Thread.currentThread()).getBytes(StandardCharsets.UTF_8));
                oos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        };
        return startVirtualThread(threadRunnable);
    }

    private long measureTimeMillis(VoidSupplier functionalInterface, String testName, String name, String timeComplexity, String spaceComplexity, Integer repeats) {
        final var startTime = LocalDateTime.now();
        functionalInterface.calculate();
        final var endTime = LocalDateTime.now();
        final long totalDurationMillis = between(startTime, endTime).toMillis();
        try (var objectOutputStream = new FileOutputStream(new File(new File(dumpDir, "java"), logFile.getName()), true)) {
            objectOutputStream.write(String.format("| Java Project Loom | %s | %s | %s | %d | %d | %s |\n",
                    String.format("%s - %s", name, testName),
                    timeComplexity, spaceComplexity,
                    repeats, totalDurationMillis, computer
            ).getBytes(StandardCharsets.UTF_8));

            final FunctionReading functionReading = new FunctionReading(String.format("%s - %s", name, testName), timeComplexity, spaceComplexity, repeats.longValue(), totalDurationMillis, -1L, computer);
            final FunctionReading destination = functionReadings.stream().filter(fr -> fr.getMethod().equals(functionReading.getMethod())).findFirst().orElse(null);

            if (destination == null) {
                functionReadings.add(functionReading);
            } else {
                destination.setJavaDuration(totalDurationMillis);
            }

            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return totalDurationMillis;
    }

    @FunctionalInterface
    private interface VoidSupplier {
        void calculate();
    }

    private void controlTest() throws InterruptedException {
        log.info("----====>>>> Starting controlTest <<<<====----");
        final var startTimeT = LocalDateTime.now();
        final var aiThread = new AtomicInteger(0);
        range(0, massiveRepeats).mapToObj(i -> {
            final Thread thread = new Thread(aiThread::getAndIncrement);
            thread.start();
            return thread;
        }).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        final var endTimeT = LocalDateTime.now();
        log.info("Imma be the main Thread");
        log.info(String.format("%d", aiThread.get()));
        log.info("It took me {} ms to finish", between(startTimeT, endTimeT).toMillis());
    }

    private void generalTest(FileOutputStream generalTestOos) throws InterruptedException, IOException {
        log.info("----====>>>> Starting generalTest <<<<====----");
        final var aiVirtualThread = new AtomicInteger(0);
        final var startTime = LocalDateTime.now();
        range(0, massiveRepeats).mapToObj(i -> startProcessAsync(aiVirtualThread::getAndIncrement, generalTestOos))
                .forEach(vt -> {
                    try {
                        vt.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

        final var endTime = LocalDateTime.now();
        log.info("Imma be the main Thread");
        log.info(String.format("%d", aiVirtualThread.get()));
        log.info("It took me {} ms to finish", between(startTime, endTime).toMillis());
    }

    private String readFullContent() throws IOException {
        return new String(Files.readAllBytes(textFile.toPath()));
    }

    private static final Logger log = LoggerFactory.getLogger(GoodStoryJavaCommand.class);
    private final String DEFAULT_MASSIVE_REPEATS = "10000";
    private final String DEFAULT_ALGORITHM_REPEATS = "10000";
}
