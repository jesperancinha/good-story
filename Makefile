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
	make sdk-unpack
sdk-linux-arch:
	curl https://download.java.net/java/early_access/loom/6/openjdk-19-loom+6-625_linux-aarch64_bin.tar.gz --output openjdk-19.tar.gz
	make sdk-unpack
sdk-unpack:
	tar -xvzf openjdk-19.tar.gz
gradle-list:
	gradle -q projects
test:
	./loom-run.sh
	java -version
	cd kotlin-good-story && gradle build test
gradle-build-test:
	gradle build test