#!/usr/bin/env bash

declare -a RUST_LIBS=("slides2-md-to-str"
                      "slides2-compile-scss")

# Rust libs
for RUST_LIB in "${RUST_LIBS[@]}"
do
   echo "$RUST_LIB"
    pushd ${RUST_LIB}
    cargo build --release
    cp target/release/*.dylib ../slides2-kt/src/main/resources/native/
    popd
done

# Generate slides
pushd slides2-kt
./gradlew assemble
# WebComponent
./gradlew deepDiveKotlin
# Refactoring Loop
# ./gradlew refactoringLoop
popd

# Build web & publish
# yarn run generate
# yarn run build
#yarn run publish
