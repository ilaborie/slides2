package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.term.Notifier.info


interface Folder {
    operator fun div(name: String): Folder

    fun exists(filename: String): Boolean

    fun resolveAbsolutePath(filename: String): String

    fun copyOrUpdate(filename: String, dest: Folder) {
        val copy = {
            dest.writeFile(filename) {
                readFileAsBytes(filename)
            }
        }
        when {
            dest.exists(filename) ->
                if (lastModified(filename) > dest.lastModified(filename)) copy()
                else info("💾: FS") { "Up to date file $filename" }
            else                  ->
                copy()
        }
    }

    fun lastModified(filename: String): Long

    fun writeTextFile(filename: String, block: () -> String)
    fun writeFile(filename: String, block: () -> ByteArray)

    fun readFileAsString(filename: String): String

    fun readFileAsBytes(filename: String): ByteArray

    fun readFileAsBase64(filename: String): String

    fun readFileAsDataUri(src: String): String =
        if (exists(src)) {
            val extension = src.split(".").last()
            val mimeType = when (extension) {
                "svg" -> "image/svg+xml;base64"
                "png" -> "image/png;base64"
                "gif" -> "image/gif;base64"
                "jpg" -> "image/jpeg;base64"
                else  -> throw IllegalArgumentException("Unsupported file extension: $extension")
            }
            "data:$mimeType,${readFileAsBase64(src)}"
        } else src

}


data class Config(val output: Folder, val input: Folder)