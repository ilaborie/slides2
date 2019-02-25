#!/usr/bin/env bash

declare -a NATIVE_CLASSES=("src/main/java/io/github/ilaborie/slides2/kt/jvm/tools/MarkdownToHtml"
                "src/main/java/io/github/ilaborie/slides2/kt/jvm/tools/ScssToCss")
HEADER_DEST=build/header


for CLASS in "${NATIVE_CLASSES[@]}"
do
   echo "$CLASS"
    javac -h ${HEADER_DEST} "${CLASS}.java"
    rm "${CLASS}.class"
done

