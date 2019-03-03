package io.github.ilaborie.slides2.kt


interface Folder {
    operator fun div(name: String): Folder
    fun exists(filename: String): Boolean
    fun resolveAbsolutePath(filename: String): String
    fun writeFile(filename: String, block: () -> String)
    fun readFileAsString(filename: String): String
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