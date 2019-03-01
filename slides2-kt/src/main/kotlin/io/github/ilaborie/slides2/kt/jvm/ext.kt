package io.github.ilaborie.slides2.kt.jvm

import java.util.*


fun ByteArray.readAsBase64() =
    Base64.getEncoder().encodeToString(this)

fun String.singleLine() =
    replace("\n", "")