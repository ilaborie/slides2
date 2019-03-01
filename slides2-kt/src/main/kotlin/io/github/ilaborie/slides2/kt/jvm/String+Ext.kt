package io.github.ilaborie.slides2.kt.jvm

import java.text.Normalizer


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
