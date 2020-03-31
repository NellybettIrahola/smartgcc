#!/bin/bash
set -e

RELEASE=1.7
JAR_NAME="google-java-format-${RELEASE}-all-deps.jar"
RELEASES_URL=https://github.com/google/google-java-format/releases/download
JAR_URL="${RELEASES_URL}/google-java-format-${RELEASE}/${JAR_NAME}"

CACHE_DIR="$HOME/.cache/google-java-format-git-pre-commit-hook"
JAR_FILE="$CACHE_DIR/$JAR_NAME"
JAR_DOWNLOAD_FILE="${JAR_FILE}.tmp"

if [[ ! -f "$JAR_FILE" ]]
then
    mkdir -p "$CACHE_DIR"
    curl -L "$JAR_URL" -o "$JAR_DOWNLOAD_FILE"
    SHASUM=`sha256sum "$JAR_DOWNLOAD_FILE" | awk '{print $1}'`
    if [ "$SHASUM" = "0894ee02019ee8b4acd6df09fb50bac472e7199e1a5f041f8da58d08730694aa" ]; then
        mv "$JAR_DOWNLOAD_FILE" "$JAR_FILE"
    else
        echo "Could not install google-java-format JAR file. Hash mismatch. Was it corrupted?"
        exit 2
    fi
fi

changed_java_files=$(git diff --cached --name-only --diff-filter=ACMR | grep ".*java$" || true)
if [[ -n "$changed_java_files" ]]
then
    echo "Reformatting Java files: $changed_java_files"
    changed_java_files=$(git diff --cached --name-only --diff-filter=ACMR | grep ".*java$" || true)
    java -jar "$JAR_FILE" --replace $changed_java_files
else
    echo "No Java files changes found."
fi
