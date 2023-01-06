MODULES := java-good-story kotlin-good-story
export GS_MASSIVE_REPEATS := 10000

b: build
pre-build:
	mvn clean install -DskipTests
build: pre-build
	mvn clean install
build-java: pre-build
	cd java-good-story && mvn clean install
build-kotlin: pre-build
	cd kotlin-good-story && mvn clean install
build-intro: pre-build
	cd coroutines-intro && mvn clean install
sdk-macos:
	curl https://download.java.net/java/early_access/loom/6/openjdk-19-loom+6-625_macos-x64_bin.tar.gz --output openjdk-19.tar.gz
	make sdk-unpack
	mv jdk-19.jdk/Contents/Home loom-jdk
	rm -r jdk-19.jdk
	rm *.tar.gz
sdk-macos-arch:
	curl https://download.java.net/java/early_access/loom/6/openjdk-19-loom+6-625_macos-aarch64_bin.tar.gz --output openjdk-19.tar.gz
	make sdk-unpack
sdk-windows:
	curl https://download.java.net/java/early_access/loom/6/openjdk-19-loom+6-625_windows-x64_bin.zip --output openjdk-19.zip
	make sdk-unpack
sdk-linux:
	curl https://download.java.net/java/early_access/loom/6/openjdk-19-loom+6-625_linux-x64_bin.tar.gz --output openjdk-19.tar.gz
	tar -xvzf openjdk-19.tar.gz && mv jdk-19 loom-jdk
	rm *.tar.gz
sdk-linux-arch:
	curl https://download.java.net/java/early_access/loom/6/openjdk-19-loom+6-625_linux-aarch64_bin.tar.gz --output openjdk-19.tar.gz
	make sdk-unpack
sdk-unpack:
	tar -xvzf openjdk-19.tar.gz
test:
	./loom-run.sh
	java -version
	cd kotlin-good-story && mvn clean install
create-headers:
	echo "| Module | Method | Time Complexity | Space Complexity | Repetitions | Measured Duration | Machine |" > dump/java/Log.md
	echo "|---|---|---|---|---|---|---|" >> dump/java/Log.md
	echo "| Module | Method | Time Complexity | Space Complexity | Repetitions | Measured Duration | Machine |" > dump/kotlin/Log.md
	echo "|---|---|---|---|---|---|---|" >> dump/kotlin/Log.md
clean:
	cd dump && rm -r java/*.csv &
	cd dump && rm -r kotlin/*.csv &
build-run:
	make create-headers
	$(foreach module,$(MODULES), \
			echo $(module) && cd $(module) && pwd && make set-jdk && make build-run && \
			cd ..)
run:
	make create-headers
	$(foreach module,$(MODULES), \
			echo $(module) && cd $(module) && pwd && make set-jdk && make run && \
			cd ..)
build-run-local:
	make create-headers
	make sub-build-run-local
sub-build-run-local:
	$(foreach module,$(MODULES), \
			echo $(module) && cd $(module) && pwd && make set-jdk && make build-run-local && \
			cd ..)
demi-mac:
	brew install cavaliercoder/dmidecode/dmidecode
os-info-max:
	/usr/sbin/system_profiler SPHardwareDataType
system-info:
	uname -mprsv
