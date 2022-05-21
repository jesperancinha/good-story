package org.jesperancinha.good.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
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
            description = "Text.md file to be processed")
    private final File textFile = null;

    @Option(names = {"-lf", "--log-file"},
            description = "Log.md file to record results",
            defaultValue = "",
            required = true)
    private final File logFile = null;

    @Option(names = {"-r", "--repeats"},
            description = {"Massive repeats"},
            defaultValue = DEFAULT_MASSIVE_REPEATS)
    private final Integer massiveRepeats = null;

    @Option(names = {"-ar", "--algo-repeats"},
            description = {"Algorithm repeats"},
            defaultValue = DEFAULT_ALGORITHM_REPEATS)
    private final Integer algoRepeats = null;


    @Option(
            names = {"-dump"},
            description = {"Dump log directory"}
    )
    private String dumpDir = null;

    @Option(
            names = {"-computer"},
            description = {"Any data, but mostly used to say on which computer model are you running"}
    )
    private String computer = null;

    @Override
    public Integer call() throws Exception {
        log.info("Welcome to the Java Project Loom Test!");
        log.info(String.format("File to read is %s", textFile));

        final var content = readFullContent();
        final var allUniqueWords = findAllUniqueWords(content);
        final var allUniqueWordsWithCount = findAllUniqueWordsWithCount(content);

        log.info("===> Text size is {}", content.length());
        log.info("===> All Words: {}", allUniqueWords.subList(0, 10));
        log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
            Thread virtualThread = null;
            for (int i = 0; i < algoRepeats; i++) {
                virtualThread = startVirtualThread(() -> findAllUniqueWords(content));
            }
            log.info("Just sent {} threads", algoRepeats);
            try {
                virtualThread.join();
            } catch (InterruptedException e) {
                log.error("Error", e);
                throw new RuntimeException(e);
            }
        }, "findAllUniqueWords", algoRepeats));
        log.info("===> All Words with count: {}", allUniqueWordsWithCount.entrySet().stream().toList().subList(0, 10));
        log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
            Thread virtualThread = null;
            for (int i = 0; i < algoRepeats; i++) {
                virtualThread = startVirtualThread(() -> findAllUniqueWordsWithCount(content));
            }
            log.info("Just sent {} threads", algoRepeats);
            try {
                virtualThread.join();
            } catch (InterruptedException e) {
                log.error("Error", e);
                throw new RuntimeException(e);
            }
        }, "findAllUniqueWordsWithCount", algoRepeats));

        log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
            try {
                controlTest(massiveRepeats);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "controlTest", massiveRepeats));
        log.info("***> Processing took {} milliseconds", measureTimeMillis(() -> {
            try {
                generalTest(massiveRepeats);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "generalTest", massiveRepeats));

        return 0;
    }

    private long measureTimeMillis(VoidSupplier functionalInterface, String name, Integer repeats) {
        final var startTime = LocalDateTime.now();
        functionalInterface.calculate();
        final var endTime = LocalDateTime.now();
        final long totalDurationMillis = between(startTime, endTime).toMillis();
        if (Objects.nonNull(logFile)) {
            try (var objectOutputStream = new FileOutputStream(logFile, true)) {
                objectOutputStream.write(String.format("| Java Project Loom | %s | %d | %d | %s |\n", name, repeats, totalDurationMillis, SystemDomain.getSystemRunningData()).getBytes(StandardCharsets.UTF_8));
                objectOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    private static void controlTest(Integer massiveRepeats) throws InterruptedException {
        log.info("----====>>>> Starting controlTest <<<<====----");
        final var startTimeT = LocalDateTime.now();
        final var aiThread = new AtomicInteger(0);
        Thread thread = null;
        for (int i = 0; i < 100000; i++) {
            thread = new Thread(aiThread::getAndIncrement);
            thread.start();
        }
        thread.join();
        final var endTimeT = LocalDateTime.now();
        log.info("Imma be the main Thread");
        log.info(String.format("%d", aiThread.get()));
        log.info("It took me " + between(startTimeT, endTimeT).getSeconds() + "s to finish");
    }

    private static void generalTest(Integer massiveRepeats) throws InterruptedException {
        log.info("----====>>>> Starting generalTest <<<<====----");
        final var aiVirtualThread = new AtomicInteger(0);
        final var startTime = LocalDateTime.now();
        Thread virtualThread = null;
        for (int i = 0; i < massiveRepeats; i++) {
            virtualThread = startVirtualThread(aiVirtualThread::getAndIncrement);
        }
        assert virtualThread != null;
        virtualThread.join();
        final var endTime = LocalDateTime.now();
        log.info("Imma be the main Thread");
        log.info(String.format("%d", aiVirtualThread.get()));
        log.info("It took me " + between(startTime, endTime).getSeconds() + "s to finish");
    }

    private static final Logger log = LoggerFactory.getLogger(GoodStoryJavaCommand.class);
    private final String DEFAULT_MASSIVE_REPEATS = "10000000";
    private final String DEFAULT_ALGORITHM_REPEATS = "100000";
}