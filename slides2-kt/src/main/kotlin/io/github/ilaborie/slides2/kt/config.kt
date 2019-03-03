package io.github.ilaborie.slides2.kt


interface Folder {
    fun exists(filename: String):Boolean
    fun writeFile(filename: String, block: () -> String)
    fun readFileAsString(filename: String): String
    fun readFileAsBase64(filename: String): String
    fun resolveAbsolutePath(filename: String): String
    operator fun div(name: String): Folder
}



data class Config(val output: Folder, val input: Folder)