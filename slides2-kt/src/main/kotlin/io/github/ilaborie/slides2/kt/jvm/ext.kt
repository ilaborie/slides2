package io.github.ilaborie.slides2.kt.jvm

import io.github.ilaborie.slides2.kt.Config
import java.io.File
import java.text.Normalizer
import java.util.*


fun String.asKey(): String =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .toLowerCase()
        .replace(Regex("[\\s]"), "-")
        .replace(Regex("[^\\p{ASCII}]"), "")
        .replace(Regex("[\\W]"), "_")

fun String.escapeHtml(): String =
    map { ch ->
        when (ch) {
            '<'  -> "&lt;"
            '>'  -> "&gt;"
            '&'  -> "&amp;"
            else -> ch.toString()
        }
    }.joinToString("")


fun ByteArray.readAsBase64(): String =
    Base64.getEncoder().encodeToString(this)

fun String.singleLine(): String =
    replace("\n", "")


fun jvmConfig(from: String, to: String): Config =
    Config(
        input = JvmFolder(File(from)),
        output = JvmFolder(File(to))
    )
