package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.plugins.ContentPlugin

object SlideEngine {

    var notifier: Notifier = Notifier.withoutTime()

    val rendererMap: MutableMap<String, MutableMap<RenderMode, Renderer<*>>> = mutableMapOf()
    private val contentPlugins: MutableList<ContentPlugin> = mutableListOf()

    inline fun <reified T : Content> registerRenderer(renderer: Renderer<T>): SlideEngine {
        rendererMap.computeIfAbsent(T::class.java.name) { mutableMapOf() }
            .put(renderer.mode, renderer)
        return this
    }

    inline fun <reified T : Content> registerRenderers(vararg renderer: Renderer<T>): SlideEngine {
        renderer.forEach { registerRenderer(it) }
        return this
    }

    fun registerContentPlugin(plugin: ContentPlugin) {
        contentPlugins += plugin
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Content> findRenderer(mode: RenderMode, content: T): Renderer<T>? =
        rendererMap[content.javaClass.name]
            ?.get(mode) as? Renderer<T>?

    inline fun <reified T : Content> render(mode: RenderMode, content: T): String =
        findRenderer(mode, content)
            ?.render(content)
            ?: throw IllegalStateException("No renderer found for $content")

    fun Presentation.render(config: Config, mode: RenderMode = Html) {
        findRenderer(mode, this)
            ?.let { renderer ->
                val filename = "$key.html"
                notifier.time("Write to $filename") {
                    config.output.writeFile(key, filename) {
                        renderer.render(this)
                    }
                }
            } ?: notifier.error { "No renderer found for $this" }
    }

}