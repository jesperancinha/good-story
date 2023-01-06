#!/usr/bin/env sh

echo -e '\033[92mChanging to the first available SDK MAN Java SDK 17 version.\033[0m'
echo -e '\033[91mRemember to run this command like this: . ./sdk17.sh\033[0m'
sdk install java 17.0.4.1-zulu
sdk use java $(sdk list java | grep installed | grep "| 17" |  cut -d'|' -f6- | cut -d' ' -f2-)
