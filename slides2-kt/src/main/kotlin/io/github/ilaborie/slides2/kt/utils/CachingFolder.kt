package io.github.ilaborie.slides2.kt.utils

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.jvm.asKey


class CachingFolder(
    private val folder: Folder,
    private val loader: (String) -> String
) {
    operator fun get(key: String): String {
        val fileName = "${key.asKey()}.cache"
        return if (folder.exists(fileName)) folder.readFileAsString(fileName)
        else loader(key)
            .also { folder.writeTextFile(fileName) { it } }
    }
}
