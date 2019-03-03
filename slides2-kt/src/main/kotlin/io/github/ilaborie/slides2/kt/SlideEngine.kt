package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.engine.*
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.extra.SpeakerHtmlRenderer
import io.github.ilaborie.slides2.kt.engine.extra.SpeakerTextRenderer
import io.github.ilaborie.slides2.kt.engine.plugins.ContentPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.*
import io.github.ilaborie.slides2.kt.jvm.tools.ScssToCss.scssFileToCss
import io.github.ilaborie.slides2.kt.term.Notifier.time
import io.github.ilaborie.slides2.kt.term.Styles

object SlideEngine {

    val rendererMap: MutableMap<String, MutableMap<RenderMode, Renderer<*>>> = mutableMapOf()
    private val contentPlugins: MutableList<ContentPlugin> = mutableListOf()

    private val cache: MutableMap<Pair<RenderMode, Presentation>, String> = mutableMapOf()

    val globalScripts: MutableList<String> = mutableListOf()

    init {
        // Base
        registerRenderers(TextTextRenderer, TextHtmlRenderer)
        registerRenderers(CompoundTextRenderer, CompoundHtmlRenderer)
        registerRenderers(TitleTextRenderer, TitleHtmlRenderer)
        registerRenderers(ParagraphTextRenderer, ParagraphHtmlRenderer)
        registerRenderers(StyledTextTextRenderer, StyledTextHtmlRenderer)
        registerRenderers(OrderedListTextRenderer, OrderedListHtmlRenderer)
        registerRenderers(UnorderedListTextRenderer, UnorderedListHtmlRenderer)
        registerRenderers(CodeTextRenderer, CodeHtmlRenderer)
        registerRenderers(LinkTextRenderer, LinkHtmlRenderer)
        registerRenderers(QuoteTextRenderer, QuoteHtmlRenderer)
        registerRenderers(NoticeTextRenderer, NoticeHtmlRenderer)
        registerRenderers(FigureTextRenderer, FigureHtmlRenderer)
        registerRenderers(TableTextRenderer, TableHtmlRenderer)
        // Extra
        registerRenderers(SpeakerTextRenderer, SpeakerHtmlRenderer)

        // Presentation, Part, Slide
        registerRenderer(PresentationHtmlRenderer())
        registerRenderer(SlideHtmlRenderer)
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

    fun Presentation.renderHtml(config: Config): PresentationOutputInstance =
        findRenderer(Html, this)
            ?.let { renderer ->
                val folder = config.output / id.id
                // Slides
                val filename = "index-${theme.name}.html"
                time("Write to ${Styles.highlight(filename)}") {
                    folder.writeFile(filename) {
                        cache.computeIfAbsent(Html to this) {
                            renderer.render(this)
                        }
                    }
                }
                // Style
                val themeFile = "${theme.name}.css"
                time("Write to ${Styles.highlight(themeFile)}") {
                    folder.writeFile(themeFile) {
                        theme.compiled
                    }
                }
                if (extraStyle != null) {
                    val outputFilename = "$extraStyle.css"
                    time("Write to ${Styles.highlight(outputFilename)}") {
                        folder.writeFile(outputFilename) {
                            val path = config.input.resolveAbsolutePath("$extraStyle.scss")
                            scssFileToCss(path)
                        }
                    }
                }
                // Scripts
                time("Write to ${globalScripts.joinToString(", ") { Styles.highlight(it) }}") {
                    globalScripts.forEach { script ->
                        folder.writeFile(script) {
                            javaClass.getResource("/scripts/$script").readText()
                        }
                    }
                }
                PresentationOutputInstance(label = theme.name, path = "${id.id}/$filename")
            } ?: throw IllegalStateException("No renderer found for $this")

    fun applyPlugins(function: () -> Presentation): Presentation =
        plugContent(function()) as Presentation

    private fun plugContent(content: Content): Content =
        plug(
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
        )

    private fun plug(content: Content): Content =
        contentPlugins.fold(content) { acc, plugin -> plugin(acc) }

}