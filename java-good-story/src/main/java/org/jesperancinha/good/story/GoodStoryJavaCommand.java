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
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.startVirtualThread;
import static java.time.Duration.between;
import static java.util.function.Function.identity;

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
        log.info("===> All Words: {}", allUniqueWords.subList(0, 10));

        final BiFunction<GoodStoryJavaCommand, String, List<String>> findAllUniqueWords = GoodStoryJavaCommand::findAllUniqueWords;


        try (FileOutputStream findAllUniqueWordsOos = new FileOutputStream(new File(new File(dumpDir, "java"), "findAllUniqueWords"), true)) {
            log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
                Thread virtualThread = null;
                for (int i = 0; i < algoRepeats; i++) {
                    virtualThread = startProcessAsync(() -> findAllUniqueWords(content), findAllUniqueWordsOos);
                }
                log.info("Just sent {} threads", algoRepeats);
                try {
                    virtualThread.join();
                } catch (InterruptedException e) {
                    log.error("Error", e);
                    throw new RuntimeException(e);
                }
            }, "findAllUniqueWords", algoRepeats));
        }

        try (FileOutputStream findAllUniqueWordsWithCountOos = new FileOutputStream(new File(new File(dumpDir, "java"), "findAllUniqueWordsWithCount"), true)) {
            log.info("===> All Words with count: {}", allUniqueWordsWithCount.entrySet().stream().toList().subList(0, 10));
            log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
                Thread virtualThread = null;
                for (int i = 0; i < algoRepeats; i++) {
                    virtualThread = startProcessAsync(() -> findAllUniqueWordsWithCount(content), findAllUniqueWordsWithCountOos);
                }
                log.info("Just sent {} threads", algoRepeats);
                try {
                    virtualThread.join();
                } catch (InterruptedException e) {
                    log.error("Error", e);
                    throw new RuntimeException(e);
                }
            }, "findAllUniqueWordsWithCount", algoRepeats));
        }
        log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
            try {
                controlTest();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "controlTest", massiveRepeats));

        try (FileOutputStream generalTestOos = new FileOutputStream(new File(new File(dumpDir, "java"), "generalTest.csv"), true)) {
            log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
                try {
                    generalTest(generalTestOos);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }, "generalTest", massiveRepeats));

        }
        return 0;
    }

    private Thread startProcessAsync(Runnable runnable, FileOutputStream oos) {
        Runnable threadRunnable = () -> {
            var start = LocalDateTime.now();
            runnable.run();
            var end = LocalDateTime.now();
            try {
                oos.write(
                        String.format("%s,%s\n", start, end).getBytes(StandardCharsets.UTF_8));
                oos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        };
        return startVirtualThread(threadRunnable);
    }

    private long measureTimeMillis(VoidSupplier functionalInterface, String name, Integer repeats) {
        final var startTime = LocalDateTime.now();
        functionalInterface.calculate();
        final var endTime = LocalDateTime.now();
        final long totalDurationMillis = between(startTime, endTime).toMillis();
        try (var objectOutputStream = new FileOutputStream(logFile, true)) {
            objectOutputStream.write(String.format("| Java Project Loom | %s | %d | %d | %s |\n",
                    name, repeats,
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

    private void controlTest() throws InterruptedException {
        log.info("----====>>>> Starting controlTest <<<<====----");
        final var startTimeT = LocalDateTime.now();
        final var aiThread = new AtomicInteger(0);
        Thread thread = null;
        for (int i = 0; i < massiveRepeats; i++) {
            thread = new Thread(aiThread::getAndIncrement);
            thread.start();
        }
        thread.join();
        final var endTimeT = LocalDateTime.now();
        log.info("Imma be the main Thread");
        log.info(String.format("%d", aiThread.get()));
        log.info("It took me {} ms to finish", between(startTimeT, endTimeT).toMillis());
    }

    private void generalTest(FileOutputStream generalTestOos) throws InterruptedException, IOException {
        log.info("----====>>>> Starting generalTest <<<<====----");
        final var aiVirtualThread = new AtomicInteger(0);
        final var startTime = LocalDateTime.now();
        Thread virtualThread = null;
        for (int i = 0; i < massiveRepeats; i++) {
            virtualThread = startProcessAsync(aiVirtualThread::getAndIncrement, generalTestOos);
        }
        assert virtualThread != null;
        virtualThread.join();
        final var endTime = LocalDateTime.now();
        log.info("Imma be the main Thread");
        log.info(String.format("%d", aiVirtualThread.get()));
        log.info("It took me {} ms to finish", between(startTime, endTime).toMillis());
    }


    private static final Logger log = LoggerFactory.getLogger(GoodStoryJavaCommand.class);
    private final String DEFAULT_MASSIVE_REPEATS = "10000";
    private final String DEFAULT_ALGORITHM_REPEATS = "10000";
}