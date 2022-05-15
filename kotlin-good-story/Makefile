include ../Makefile
.SILENT: build-run

run:
	bash run.sh
set-jdk:
	java -version
build-run:
	echo "╔═══════════════════════════════════════════════════╗"
	echo "║ Welcome to the Kotlin Good Story Performance Test ║"
	echo "╚═══════════════════════════════════════════════════╝"
	gradle build test
	make run
