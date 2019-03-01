package io.github.ilaborie.slides2.kt.jvm

import java.util.*


fun ByteArray.readAsBase64(): String =
    Base64.getEncoder().encodeToString(this)

fun String.singleLine(): String =
    replace("\n", "")