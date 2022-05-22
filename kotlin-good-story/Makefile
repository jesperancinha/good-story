include ../Makefile
.SILENT: build-run build-run-local
SHELL := /bin/bash

run:
	bash run.sh
run-local:
	bash run-local.sh
set-jdk:
	cd .. && source loom-jdk.sh
build-run:
	echo "╔═══════════════════════════════════════════════════╗"
	echo "║ Welcome to the Kotlin Good Story Performance Test ║"
	echo "╚═══════════════════════════════════════════════════╝"
	bash build-run.sh
build-run-local:
	echo "╔═══════════════════════════════════════════════════════════╗"
	echo "║ Welcome to the Kotlin Good Story Performance Test (local) ║"
	echo "╚═══════════════════════════════════════════════════════════╝"
	bash build-run-local.sh

