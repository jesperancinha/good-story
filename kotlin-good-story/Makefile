include ../Makefile
.SILENT: build-run
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
	gradle build
	make run
build-run-local:
	echo "╔═══════════════════════════════════════════════════════════╗"
	echo "║ Welcome to the Kotlin Good Story Performance Test (local) ║"
	echo "╚═══════════════════════════════════════════════════════════╝"
	gradle build
	make run-local
