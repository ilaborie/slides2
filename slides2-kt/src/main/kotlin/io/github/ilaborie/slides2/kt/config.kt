package io.github.ilaborie.slides2.kt

interface Folder {
    fun writeFile(parent: String, filename: String, block: () -> String)
}


interface Config {
    val dest: Folder
}