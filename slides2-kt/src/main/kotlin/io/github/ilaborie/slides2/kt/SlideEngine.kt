package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.cli.Styles
import io.github.ilaborie.slides2.kt.engine.*
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.plugins.ContentPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.*

object SlideEngine {

    var notifier: Notifier = Notifier.withoutTime()

    val rendererMap: MutableMap<String, MutableMap<RenderMode, Renderer<*>>> = mutableMapOf()
    private val contentPlugins: MutableList<ContentPlugin> = mutableListOf()

    init {
        // Base
        registerRenderers(TextTextRenderer, TextHtmlRenderer)
        registerRenderers(TitleTextRenderer, TitleHtmlRenderer)
        registerRenderers(ParagraphTextRenderer, ParagraphHtmlRenderer)
        registerRenderers(StyledTextTextRenderer, StyledTextHtmlRenderer)
        registerRenderers(OrderedListHtmlRenderer, OrderedListHtmlRenderer)
        registerRenderers(UnorderedListHtmlRenderer, UnorderedListHtmlRenderer)

        // Presentation, Part, Slide
        registerRenderer(PresentationHtmlRenderer)
        registerRenderer(SlideHtmlRenderer)
//        registerRenderer(PartHtmlRenderer)
    }

    inline fun <reified T : Content> registerRenderer(renderer: Renderer<T>): SlideEngine {
        val map = rendererMap.computeIfAbsent(T::class.java.name) { mutableMapOf() }
        map[renderer.mode] = renderer
        return this
    }

    inline fun <reified T : Content> registerRenderers(vararg renderer: Renderer<T>): SlideEngine {
        renderer.forEach { registerRenderer(it) }
        return this
    }

    fun registerContentPlugin(plugin: ContentPlugin): SlideEngine {
        contentPlugins += plugin
        return this
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
                val filename = "${id.id}.html"
                notifier.time("Write to ${Styles.highlight(filename)}") {
                    config.output.writeFile(id.id, filename) {
                        renderer.render(this)
                    }
                }
            } ?: notifier.error { "No renderer found for $this" }
    }

    fun applyPlugins(function: () -> Presentation): Presentation =
        plugContent(function()) as Presentation

    private fun plugContent(content: Content): Content =
        when (content) {
            is Presentation -> content.copy(
                title = plug(content.title),
                parts = content.parts
                    .map(this::plugContent)
                    .filterIsInstance<Part>()
            )
            is Part         -> content.copy(
                title = plug(content.title),
                slides = content.slides
                    .map(this::plugContent)
                    .filterIsInstance<Slide>()
            )
            is Slide        -> content.copy(
                title = plug(content.title),
                content = content.content
                    .map(this::plugContent)
            )
            else            -> plug(content)
        }

    private fun plug(content: Content): Content =
        contentPlugins.fold(content) { acc, plugin -> plugin(acc) }

}