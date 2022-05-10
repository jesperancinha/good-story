#!/bin/bash
java -jar --enable-preview  target/java-good-story-1.0-SNAPSHOT.jar -h
java -jar --enable-preview  target/java-good-story-1.0-SNAPSHOT.jar -f "$(git rev-parse --show-toplevel)"/GoodStory.md