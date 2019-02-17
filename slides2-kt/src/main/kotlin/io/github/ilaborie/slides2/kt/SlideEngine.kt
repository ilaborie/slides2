package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.plugins.ContentPlugin

object SlideEngine {

    var notifier: Notifier = Notifier.withoutTime()

    val renderers: MutableMap<String, Renderer> = mutableMapOf()
    private val contentPlugins: MutableList<ContentPlugin> = mutableListOf()

    inline fun <reified T> registerRenderer(renderer: Renderer) {
        renderers[T::class.java.name] = renderer
    }

    fun registerContentPlugin(plugin: ContentPlugin) {
        contentPlugins += plugin
    }

    private fun findRenderer(mode: RenderMode, content: Content): Renderer? =
        renderers[content.javaClass.name]

    fun Presentation.render(config: Config, mode: RenderMode = Renderer.Companion.RenderMode.Html) {
        findRenderer(mode, this)
            ?.let { renderer ->
                val filename = "$key.html"
                notifier.time("Write to $filename") {
                    config.dest.writeFile(key, filename) {
                        renderer.render()
                    }
                }
            } ?: notifier.error { "No renderer found for $this" }
    }

}