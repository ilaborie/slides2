package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Content

interface ContentPlugin {
    val name: String

    operator fun invoke(content: Content): Content

}