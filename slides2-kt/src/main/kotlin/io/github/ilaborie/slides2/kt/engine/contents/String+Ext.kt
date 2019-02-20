package io.github.ilaborie.slides2.kt.engine.contents


fun String.header(level: Int): Title =
    Title(level, Text(this))

val String.h1: Title
    get() = header(1)

val String.h2: Title
    get() = header(2)

val String.h3: Title
    get() = header(3)

val String.h4: Title
    get() = header(4)

val String.h5: Title
    get() = header(5)

val String.h6: Title
    get() = header(6)