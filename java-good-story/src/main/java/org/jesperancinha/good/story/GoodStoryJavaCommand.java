package org.jesperancinha.good.story;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.lang.Thread.startVirtualThread;
import static java.time.Duration.between;
import static java.util.function.Function.identity;
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

        final var content = readFullContent();
        final var allUniqueWords = findAllUniqueWords(content);
        final var allUniqueWordsWithCount = findAllUniqueWordsWithCount(content);

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


        log.info("===> Text size is {}", content.length());

        performTest(
                "All Unique Words",
                "findAllUniqueWords",
                "n/a", "n/a", () -> String.join(".",
                        allUniqueWords.subList(0, 10)),
                () -> findAllUniqueWords(content), algoRepeats);

        performTest(
                "All Words with count",
                "findAllUniqueWordsWithCount",
                "n/a", "n/a",
                () -> String.join(".",
                        allUniqueWordsWithCount.entrySet().stream().map((entry) ->
                                String.format("%s to %d", entry.getKey(), entry.getValue())).toList().subList(0, 10)),
                () -> findAllUniqueWordsWithCount(content), algoRepeats);

        performTest(
                "Reverted Text",
                "revertText",
                "O(n)", "O(1)",
                () -> revertText("Lucy meets Menna and the Fish"),
                () -> revertText(content), algoRepeats);

        performTest(
                "Double iteration of an array of words",
                "contentSplitIterateSubtractAndSum",
                "O(n^2)", "O(1)",
                () -> contentSplitIterateSubtractAndSum("Oh there you are Mr. Fallout!"),
                () -> contentSplitIterateSubtractAndSum(content), algoRepeats);

        performTest(
                "Repetition count",
                "repetitionCount",
                "O(n^2)", "O(1)",
                () -> repetitionCount("I know he let the dog bark and he was using slack to support a production crisis, but that doesn't mean he can't perform an interview at the same time right?"),
                () -> repetitionCount(content), algoRepeats);

        performGenericTests();

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
        return 0;
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
            log.info("===> {}: {}", testName, sampleTest.get());
            log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
                final List<Thread> threadStream = range(0, massiveRepeats)
                        .mapToObj(i -> startProcessAsync(toTest, oos)).toList();
                log.info("Just sent {} threads", repeats);
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

    /**
     * Double iteration of an array of words.
     * Result is the absolute sum of all the differences of sizes between words
     * This function follows has a quadratic big O time complexity notation of O(n^2) and a space complexity of O(1)
     */
    Integer contentSplitIterateSubtractAndSum(String content) {
        var allWords = findAllUniqueWords(content);
        var sum = 0;
        for (String element : allWords)
            for (int j = allWords.size() - 1; j >= 0; j--) {
                sum += abs(element.length() - allWords.get(j).length());
            }
        return sum;
    }

    /**
     * Counts how many repeated words are in text.
     * If one word is repeated 3 times, that counts as 2 repetitions.
     * The result is a sum of all of these repetitions per word.
     */
    Long repetitionCount(String content) {
        return Arrays.stream(content.split(" "))
                .map(word -> word.replaceAll(",", "").replaceAll("\\.", "").replaceAll("\\?", "").toLowerCase())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values().stream().filter(repetitions -> repetitions > 1)
                .reduce(0L, (a, b) -> a + b - 1);
    }

    /**
     * Reverts a string using a space complexity of O(1) and a time complexity of O(n)
     *
     * @param content Content
     * @return Reverted String content
     */
    String revertText(String content) {
        final char[] charArray = content.toCharArray();
        for (int i = 0; i < charArray.length / 2; i++) {
            char c = charArray[i];
            charArray[i] = charArray[charArray.length - i - 1];
            charArray[charArray.length - i - 1] = c;
        }
        return new String(charArray);
    }

    Map<String, Long> findAllUniqueWordsWithCount(String content) {
        return makeWordsList(content).sorted().collect(Collectors.groupingBy(identity(), Collectors.counting()));
    }

    List<String> findAllUniqueWords(String content) {
        return makeWordsList(content).distinct().collect(Collectors.toList());
    }

    private Stream<String> makeWordsList(String content) {
        return Arrays.stream(content.split(" ")).sorted().filter(GoodStoryJavaCommand::filterWords);
    }

    private static boolean filterWords(String possibleWord) {
        return possibleWord.matches("[a-zA-Z]+");
    }

    private String readFullContent() throws IOException {
        return new String(Files.readAllBytes(textFile.toPath()));
    }

    private static final Logger log = LoggerFactory.getLogger(GoodStoryJavaCommand.class);
    private final String DEFAULT_MASSIVE_REPEATS = "10000";
    private final String DEFAULT_ALGORITHM_REPEATS = "10000";
}
