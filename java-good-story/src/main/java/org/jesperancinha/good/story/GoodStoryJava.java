/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.jesperancinha.good.story;

import picocli.CommandLine;

public class GoodStoryJava {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new GoodStoryJavaCommand()).execute(args);
        System.exit(exitCode);
    }
}