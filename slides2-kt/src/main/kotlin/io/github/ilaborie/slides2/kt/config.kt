package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.cli.Notifier


interface Folder {
    fun writeFile(filename: String, block: () -> String)
    fun readFileAsString(filename: String): String
    fun resolveAbsolutePath(filename: String): String
    operator fun div(name: String): Folder
}

data class Config(
    val output: Folder,
    val input: Folder,
    val notifier: Notifier
)