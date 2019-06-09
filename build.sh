#!/usr/bin/env bash

# Rust lib
pushd slides2-rust
    cargo build --release
    cp target/release/*.dylib ../slides2-kt/src/main/resources/native/
popd

# Generate slides
pushd slides2-kt
    ./gradlew assemble

    # CSS Clockwork
    ./gradlew cssClockwork

    # WebComponent
    # ./gradlew deepDiveKotlin

    # Refactoring Loop
    # ./gradlew refactoringLoop
popd
