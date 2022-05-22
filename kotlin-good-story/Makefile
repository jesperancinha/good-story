include ../Makefile
.SILENT: build-run build-run-local
SHELL := /bin/bash

run:
	bash run.sh
run-local:
	bash run-local.sh
set-jdk:
	java -version
build-run:
	echo "╔═══════════════════════════════════════════════════╗"
	echo "║ Welcome to the Kotlin Good Story Performance Test ║"
	echo "╚═══════════════════════════════════════════════════╝"
	mvn clean install -DskipTests
	make run
build-run-local:
	echo "╔═══════════════════════════════════════════════════════════╗"
	echo "║ Welcome to the Kotlin Good Story Performance Test (local) ║"
	echo "╚═══════════════════════════════════════════════════════════╝"
	mvn clean install -DskipTests
	make run-local
