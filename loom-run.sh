#!/bin/bash
export JAVA_HOME=$(pwd)/loom-jdk
export PATH=$(pwd)/loom-jdk/bin:$PATH
echo "JAVA_HOME=""$JAVA_HOME"
echo "PATH=""$PATH"
env | grep JAVA
java -version
cd java-good-story && gradle build test
