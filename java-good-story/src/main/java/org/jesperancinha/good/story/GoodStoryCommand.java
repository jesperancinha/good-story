package org.jesperancinha.good.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

/**
 * Created by jofisaes on 10/05/2022
 */
@Command(name = "GoodStory Java Algorithms",
        mixinStandardHelpOptions = true,
        version = "checksum 4.0",
        description = "Test Algorithms and measures their performance time")
class GoodStoryCommand implements Callable<Integer> {

    @Option(names = {"-f", "--file"},
            description = "Text.md file to be processed",
            defaultValue = "",
            required = true)
    private File textFile = null;

    @Option(
            names = {"-r", "--repeats"},
            description = {"Massive repeats"},
            defaultValue = DEFAULT_MASSIVE_REPEATS
    )
    private Integer massiveRepeats = null;

    @Option(
            names = {"-ar", "--algo-repeats"},
            description = {"Algorithm repeats"},
            defaultValue = DEFAULT_ALGORITHM_REPEATS
    )
    private Integer algoRepeats = null;

    @Override
    public Integer call() throws Exception {

        log.info(String.format("File to read is %s", textFile));

        final var content = readFullContent();
        final var allUniqueWords = findAllUniqueWords(content);
        final var allUniqueWordsWithCount = findAllUniqueWordsWithCount(content);

        log.info("===> Text size is {}", content.length());
        log.info("===> All Words: {}", allUniqueWords.subList(0, 10));
        log.info("===> All Words with count: {}", allUniqueWordsWithCount.entrySet().stream().toList().subList(0, 10));

        generalTest();
        return 0;
    }

    Map<String, Long> findAllUniqueWordsWithCount(String content) {
        return makeWordsList(content)
                .sorted()
                .collect(Collectors.groupingBy(
                        identity(), Collectors.counting()));
    }

    List<String> findAllUniqueWords(String content) {
        return makeWordsList(content)
                .distinct()
                .collect(Collectors.toList());
    }

    private Stream<String> makeWordsList(String content) {
        return Arrays.stream(content.split(" "))
                .sorted()
                .filter(GoodStoryCommand::filterWords);
    }

    private static boolean filterWords(String possibleWord) {
        return possibleWord.matches("[a-zA-Z]+");
    }

    private String readFullContent() throws IOException {
        return new String(Files.readAllBytes(textFile.toPath()));
    }

    private void generalTest() throws InterruptedException {
        log.info("Welcome to the Java Project Loom Test!");
        final var aiVirtualThread = new AtomicInteger(0);
        final var startTime = LocalDateTime.now();
        Thread virtualThread = null;
        for (int i = 0; i < 10000000; i++) {
            virtualThread = Thread.startVirtualThread(aiVirtualThread::getAndIncrement);
        }
        virtualThread.join();
        final var endTime = LocalDateTime.now();
        log.info("Imma be the main Thread");
        log.info(String.format("%d", aiVirtualThread.get()));
        log.info("It took me " + Duration.between(startTime, endTime).getSeconds() + "s to finish");


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
        log.info("It took me " + Duration.between(startTimeT, endTimeT).getSeconds() + "s to finish");
    }

    private static final Logger log = LoggerFactory.getLogger(GoodStoryCommand.class);
    private final String DEFAULT_MASSIVE_REPEATS = "10000000";
    private final String DEFAULT_ALGORITHM_REPEATS = "1000";
}