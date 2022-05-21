#!/bin/bash
export JAVA_HOME=$(git rev-parse --show-toplevel)/loom-jdk
export PATH=$(git rev-parse --show-toplevel)/loom-jdk/bin:$PATH
echo "JAVA_HOME=""$JAVA_HOME"
echo "PATH=""$PATH"
env | grep JAVA
java -version
cd java-good-story && gradle build test
