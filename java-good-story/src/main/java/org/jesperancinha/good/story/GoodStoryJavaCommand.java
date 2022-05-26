package org.jesperancinha.good.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
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

        log.info("===> Text size is {}", content.length());

        performTest(
                "All Unique Words",
                "findAllUniqueWords",
                () -> String.join(".",
                        allUniqueWords.subList(0, 10)),
                () -> findAllUniqueWords(content), algoRepeats);

        performTest(
                "All Words with count",
                "findAllUniqueWordsWithCount",
                () -> String.join(".",
                        allUniqueWordsWithCount.entrySet().stream().map((entry) ->
                                String.format("%s to %d", entry.getKey(), entry.getValue())).toList().subList(0, 10)),
                () -> findAllUniqueWordsWithCount(content), algoRepeats);

        performTest(
                "Reverted Text with space complexity of O(1) and a time complexity of O(n)",
                "revertText",
                () -> revertText("Lucy meets Menna and the Fish"),
                () -> revertText(content), algoRepeats);

        performTest(
                "Double iteration of an array of words with Space complexity of O(1) and a time complexity of O(n^2)",
                "contentSplitIterateSubtractAndSum",
                () -> contentSplitIterateSubtractAndSum("Oh there you are Mr. Fallout!"),
                () -> contentSplitIterateSubtractAndSum(content), algoRepeats);

        performGenericTests();
        return 0;
    }

    private void performGenericTests() throws IOException {
        log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
            try {
                controlTest();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "N/A", "controlTest", massiveRepeats));

        try (FileOutputStream generalTestOos = new FileOutputStream(new File(new File(dumpDir, "java"), "generalTest.csv"), true)) {
            log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
                try {
                    generalTest(generalTestOos);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }, "N/A", "generalTest", massiveRepeats));

        }
    }

    private <T> void performTest(String testName, String methodName, Supplier<T> sampleTest, Runnable toTest, int repeats) {
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
            }, testName, methodName, repeats));
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

    private long measureTimeMillis(VoidSupplier functionalInterface, String testName, String name, Integer repeats) {
        final var startTime = LocalDateTime.now();
        functionalInterface.calculate();
        final var endTime = LocalDateTime.now();
        final long totalDurationMillis = between(startTime, endTime).toMillis();
        try (var objectOutputStream = new FileOutputStream(new File(new File(dumpDir, "java"),logFile.getName()), true)) {
            objectOutputStream.write(String.format("| Java Project Loom | %s | %d | %d | %s |\n",
                    String.format("%s - %s", name, testName), repeats,
                    totalDurationMillis, computer
            ).getBytes(StandardCharsets.UTF_8));
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
