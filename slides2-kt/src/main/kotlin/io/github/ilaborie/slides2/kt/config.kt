package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.cli.Notifier


interface Folder {
    fun writeFile(parent: String, filename: String, block: () -> String)
}

data class Config(
    val output: Folder,
    val notifier: Notifier
)