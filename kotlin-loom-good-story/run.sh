#!/bin/bash
source loom-jdk.sh
java -version
java -jar --enable-preview target/kotlin-loom-good-story.jar -h
java -jar --enable-preview target/kotlin-loom-good-story.jar -f "$(git rev-parse --show-toplevel)"/docs/good.story/GoodStory.md \
                                          -lf "$(git rev-parse --show-toplevel)"/Log.md \
                                          -dump "$(git rev-parse --show-toplevel)"/dump
