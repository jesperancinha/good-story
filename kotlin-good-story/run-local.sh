#!/bin/bash
java -version
java -jar target/kotlin-good-story.jar -h
java -jar target/kotlin-good-story.jar  -f "$(git rev-parse --show-toplevel)"/docs/good.story/GoodStory.md \
                                            -lf "$(git rev-parse --show-toplevel)"/Log.md \
                                            -dump "$(git rev-parse --show-toplevel)"/dump \
                                            -r "$GS_MASSIVE_REPEATS"
