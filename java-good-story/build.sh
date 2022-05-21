#!/bin/bash
source loom-jdk.sh
echo "$JAVA_HOME"
java -version
mvn clean install
source run.sh
