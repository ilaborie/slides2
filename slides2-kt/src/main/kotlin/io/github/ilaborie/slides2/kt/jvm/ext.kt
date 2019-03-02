package io.github.ilaborie.slides2.kt.jvm

import io.github.ilaborie.slides2.kt.Config
import java.io.File
import java.util.*


fun ByteArray.readAsBase64(): String =
    Base64.getEncoder().encodeToString(this)

fun String.singleLine(): String =
    replace("\n", "")


fun jvmConfig(from: String, to: String): Config =
    Config(
        input = JvmFolder(File(from)),
        output = JvmFolder(File(to))
    )
