#!/usr/bin/env bash

NATIVE_CLASS="src/main/java/io/github/ilaborie/slides2/RustLib.java"
HEADER_DEST=.

javac -h ${HEADER_DEST} ${NATIVE_CLASS}