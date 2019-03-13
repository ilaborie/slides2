package io.github.ilaborie.slides2.kt.jvm

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.term.Notifier.debug
import io.github.ilaborie.slides2.kt.utils.CachingFolder
import java.io.File
import java.net.URL


class JvmFolder(private val file: File) : Folder {

    private val stringCache: MutableMap<String, String> = mutableMapOf()
    private val bytesCache: MutableMap<String, ByteArray> = mutableMapOf()

    // Need lazy to avoid stackOverflow
    private val urlCache: CachingFolder by lazy {
        CachingFolder(JvmFolder(".cache/url")) { url ->
            URL(url).readText()
        }
    }

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

    override fun lastModified(filename: String): Long =
        resolve(filename).lastModified()

    override fun writeTextFile(filename: String, block: () -> String) {
        resolve(filename)
            .also { if (it.exists()) debug("💾: FS") { "Override file $it" } }
            .writeText(block())
    }

    override fun writeFile(filename: String, block: () -> ByteArray) {
        resolve(filename)
            .also { if (it.exists()) debug("💾: FS") { "Override file $it" } }
            .writeBytes(block())
    }

    override fun writeUrlContent(url: String) {
        val filename = url.split("/").last()
        writeTextFile(filename) {
            urlCache[url]
        }
    }


    override fun readFileAsString(filename: String): String =
        resolve(filename)
            .let { file ->
                stringCache.computeIfAbsent(filename) {
                    debug("💾: FS") { "Read file $filename" }
                    file.readText()
                }
            }

    override fun readFileAsBytes(filename: String): ByteArray =
        resolve(filename)
            .let { file ->
                bytesCache.computeIfAbsent(filename) {
                    debug("💾: FS") { "Read file $filename as Base64" }
                    file.readBytes()
                }
            }

    override fun readFileAsBase64(filename: String): String =
        readFileAsBytes(filename).readAsBase64()

    override fun resolveAbsolutePath(filename: String): String =
        resolve(filename)
            .absolutePath

    override fun div(name: String): Folder =
        JvmFolder(file.resolve(name))

}