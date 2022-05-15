#!/bin/bash
java -jar --enable-preview  target/java-good-story.jar -h
java -jar --enable-preview  target/java-good-story.jar -f "$(git rev-parse --show-toplevel)"/GoodStory.md -lf "$(git rev-parse --show-toplevel)"/Log.md
