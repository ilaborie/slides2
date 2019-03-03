package io.github.ilaborie.slides2.kt.jvm

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.term.Notifier.debug
import io.github.ilaborie.slides2.kt.term.Notifier.info
import java.io.File


class JvmFolder(private val file: File) : Folder {

    private val stringCache: MutableMap<String, String> = mutableMapOf()
    private val bytesCache: MutableMap<String, ByteArray> = mutableMapOf()

    constructor(path: String) : this(File(path))

    init {
        require(file.isDirectory || !file.exists()) { "Expected a folder, and $file already exists" }
    }

    private fun resolve(vararg path: String): File =
        path.fold(file) { acc, elt -> acc.resolve(elt) }
            .also { parent ->
                if (parent.parentFile.mkdirs()) {
                    debug("💾: FS") { "Create folder ${parent.parentFile}" }
                }
            }

    override fun exists(filename: String): Boolean =
        resolve(filename).exists()

    override fun writeFile(filename: String, block: () -> String) {
        resolve(filename)
            .also { if (it.exists()) info("💾: FS") { "Override file $it" } }
            .writeText(block())
    }

    override fun readFileAsString(filename: String): String =
        resolve(filename)
            .let { file ->
                stringCache.computeIfAbsent(filename) {
                    info("💾: FS") { "Read file $filename" }
                    file.readText()
                }
            }

    override fun readFileAsBase64(filename: String): String =
        resolve(filename)
            .let { file ->
                bytesCache.computeIfAbsent(filename) {
                    info("💾: FS") { "Read file $filename as Base64" }
                    file.readBytes()
                }
                    .readAsBase64()
            }

    override fun resolveAbsolutePath(filename: String): String =
        resolve(filename)
            .absolutePath

    override fun div(name: String): Folder =
        JvmFolder(file.resolve(name))

}