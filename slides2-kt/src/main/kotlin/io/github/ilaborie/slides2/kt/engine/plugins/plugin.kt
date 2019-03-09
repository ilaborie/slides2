package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Stylesheet

interface Plugin {
    val name: String
}


interface ContentPlugin : Plugin {

    operator fun invoke(content: Content): Content

}

interface WebPlugin : Plugin {

    fun scripts(): List<Script> =
        listOf()

    fun stylesheets(): List<Stylesheet> =
        listOf()

}

interface RendererPlugin<T : Content> : Plugin {

    val clazz: Class<T>

    fun renderers(): List<Renderer<T>>
}
