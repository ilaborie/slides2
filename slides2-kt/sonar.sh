#!/usr/bin/env bash

sonar-scanner \
    -Dsonar.projectKey=slides2 \
    -Dsonar.sources=. \
    -Dsonar.host.url=http://localhost:9000 \
    -Dsonar.login=100feda51c2c7ae2d212059d2ef15a6397dac6ab \
    -Dsonar.java.binaries=build/classes/ \
    -Dsonar.coverage.exclusions=build/*
