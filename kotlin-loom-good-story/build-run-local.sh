#!/bin/bash
echo "$JAVA_HOME"
java -version
mvn clean install -DskipTests
source run-local.sh
