package org.jesperancinha.good.story;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jofisaes on 10/05/2022
 */
@Command(name = "checksum",
        mixinStandardHelpOptions = true,
        version = "checksum 4.0",
        description = "Prints the checksum (SHA-256 by default) of a file to STDOUT.")
class GoodStoryCommand implements Callable<Integer> {

    @Parameters(index = "0",
            description = "The file whose checksum to calculate.",
            defaultValue = "")
    private File file;

    @Option(names = {"-f", "--file"},
            description = "Text.md file to be processed",
            defaultValue = "")
    private File textFile = null;

    GoodStoryCommand() {
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...

        System.out.println(file);
        System.out.println(textFile);

        String content = new String(Files.readAllBytes(textFile.toPath()));

        System.out.println(content);

        System.out.println("Welcome to the Java Project Loom Test!");
        final var aiVirtualThread = new AtomicInteger(0);
        final var startTime = LocalDateTime.now();
        Thread virtualThread = null;
        for (int i = 0; i < 10000000; i++) {
            virtualThread = Thread.startVirtualThread(aiVirtualThread::getAndIncrement);
        }
        virtualThread.join();
        final var endTime = LocalDateTime.now();
        System.out.println("Imma be the main Thread");
        System.out.println(aiVirtualThread.get());
        System.out.println("It took me " + Duration.between(startTime, endTime).getSeconds() + "s to finish");


        final var startTimeT = LocalDateTime.now();
        final var aiThread = new AtomicInteger(0);
        Thread thread = null;
        for (int i = 0; i < 100000; i++) {
            thread = new Thread(aiThread::getAndIncrement);
            thread.start();
        }
        thread.join();
        final var endTimeT = LocalDateTime.now();
        System.out.println("Imma be the main Thread");
        System.out.println(aiThread.get());
        System.out.println("It took me " + Duration.between(startTimeT, endTimeT).getSeconds() + "s to finish");
        return 0;
    }

}