package io.github.ilaborie.slides2.kt.jvm

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.cli.Notifier
import java.io.File


class JvmFolder(private val file: File, val notifier: Notifier) : Folder {

    init {
        require(file.isDirectory || !file.exists()) { "Expected a folder, and $file already exists" }
    }

    private fun resolve(vararg path: String): File =
        path.fold(file) { acc, elt -> acc.resolve(elt) }
            .also { parent ->
                if (parent.parentFile.mkdirs()) {
                    notifier.debug("💾: FS") { "Create folder ${parent.parentFile}" }
                }
            }

    override fun exists(filename: String): Boolean =
        resolve(filename).exists()

    override fun writeFile(filename: String, block: () -> String) {
        resolve(filename)
            .also { if (it.exists()) notifier.info("💾: FS") { "Override file $it" } }
            .writeText(block())
    }

    override fun readFileAsString(filename: String): String =
        resolve(filename)
            .also { notifier.info("💾: FS") { "Read file ${it.absolutePath}" } }
            .let { file ->
                when (file.extension) {
                    "svg"               -> file.readText().singleLine().replace('"', '\'')
                    "png", "jpg", "gif" -> file.readBytes().readAsBase64()
                    else                -> file.readText()
                }
            }

    override fun resolveAbsolutePath(filename: String): String =
        resolve(filename)
            .absolutePath

    override fun div(name: String): Folder =
        JvmFolder(file.resolve(name), notifier)

}