package io.github.ilaborie.slides2.kt.jvm

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.cli.Notifier
import java.io.File


class JvmFolder(val file: File, val notifier: Notifier) : Folder {

    init {
        require(file.isDirectory || !file.exists()) { "Expected a folder, and $file already exists" }
    }


    private fun resolve(vararg path: String): File =
        path.fold(file) { acc, elt -> acc.resolve(elt) }
            .also { parent ->
                if (parent.parentFile.mkdirs()) {
                    notifier.debug("FS") { "Create folder ${parent.parentFile}" }
                }
            }

    override fun writeFile(parent: String, filename: String, block: () -> String) {
        resolve(parent, filename)
            .also { if (it.exists()) notifier.info("FS") { "Override file $it" } }
            .writeText(block())
    }
}