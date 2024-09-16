include ../Makefile
.SILENT: build-run build-run-local run
SHELL := /bin/bash

set-jdk:
	cd .. && source loom-jdk.sh
run:
	echo "╔═════════════════════════════════════════════════════╗"
	echo "║ Welcome to the Java Good Story Run Performance Test ║"
	echo "╚═════════════════════════════════════════════════════╝"
	bash run.sh
build-run:
	echo "╔═════════════════════════════════════════════════╗"
	echo "║ Welcome to the Java Good Story Performance Test ║"
	echo "╚═════════════════════════════════════════════════╝"
	bash build-run.sh
build-run-local:
	echo "╔═════════════════════════════════════════════════════════╗"
	echo "║ Welcome to the Java Good Story Performance Test (local) ║"
	echo "╚═════════════════════════════════════════════════════════╝"
	bash build-run-local.sh
