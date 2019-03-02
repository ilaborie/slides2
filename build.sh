#!/usr/bin/env bash

declare -a RUST_LIBS=("slides2-md-to-str"
                      "slides2-compile-scss")


for RUST_LIB in "${RUST_LIBS[@]}"
do
   echo "$RUST_LIB"
    pushd ${RUST_LIB}
    cargo build --release
    cp target/release/*.dylib ../slides2-kt/src/main/resources/native/
    popd
done


pushd slides2-kt
./gradlew clean assemble
popd

