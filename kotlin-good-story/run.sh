#!/bin/bash
java -jar build/libs/kotlin-good-story.jar -h
java -jar build/libs/kotlin-good-story.jar  -f "$(git rev-parse --show-toplevel)"/GoodStory.md -lf "$(git rev-parse --show-toplevel)"/Log.md
