#!/usr/bin/env bash

main() {
    local compileOutput
    compileOutput=$(./gradlew shadowJar 2>&1)

    if [ $? -ne 0 ]; then
        echo "$compileOutput"
        fatal "Error building prophecy compiler from source"
    fi

    java -jar ./build/libs/prophecy-*-all.jar "$@"
}

fatal() {
    local errorMessage="$1"

    error "$errorMessage"
    exit 1
}

error() {
	printf '\E[31m'; echo "$@"; printf '\E[0m'
}

main "$@"
