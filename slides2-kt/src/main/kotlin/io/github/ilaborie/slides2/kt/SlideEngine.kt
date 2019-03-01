package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.cli.Styles
import io.github.ilaborie.slides2.kt.engine.*
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.plugins.ContentPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.*
import io.github.ilaborie.slides2.kt.jvm.tools.HtmlToPdf

object SlideEngine {

    var notifier: Notifier = Notifier.withoutTime()

    val rendererMap: MutableMap<String, MutableMap<RenderMode, Renderer<*>>> = mutableMapOf()
    private val contentPlugins: MutableList<ContentPlugin> = mutableListOf()

    private val cache: MutableMap<Pair<RenderMode, Presentation>, String> = mutableMapOf()

    val globalScripts: MutableList<String> = mutableListOf()

    init {
        // Base
        registerRenderers(TextTextRenderer, TextHtmlRenderer)
        registerRenderers(TitleTextRenderer, TitleHtmlRenderer)
        registerRenderers(ParagraphTextRenderer, ParagraphHtmlRenderer)
        registerRenderers(StyledTextTextRenderer, StyledTextHtmlRenderer)
        registerRenderers(OrderedListTextRenderer, OrderedListHtmlRenderer)
        registerRenderers(UnorderedListTextRenderer, UnorderedListHtmlRenderer)
        registerRenderers(CodeTextRenderer, CodeHtmlRenderer)
        registerRenderers(LinkTextRenderer, LinkHtmlRenderer)
        registerRenderers(QuoteTextRenderer, QuoteHtmlRenderer)

        // Presentation, Part, Slide
        registerRenderer(PresentationHtmlRenderer())
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

    fun Presentation.renderHtml(config: Config) {
        findRenderer(Html, this)
            ?.let { renderer ->
                val folder = config.output / id.id
                // Slides
                val filename = "index-${theme.name}.html"
                notifier.time("Write to ${Styles.highlight(filename)}") {
                    folder.writeFile(filename) {
                        cache.computeIfAbsent(Html to this) {
                            renderer.render(this)
                        }
                    }
                }
                // Style
                val themeFile = "${theme.name}.css"
                notifier.time("Write to ${Styles.highlight(themeFile)}") {
                    folder.writeFile(themeFile) {
                        theme.compiled
                    }
                }
                // Scripts
                notifier.time("Write to ${globalScripts.joinToString(", ") { Styles.highlight(it)} }}") {
                        globalScripts.forEach { script ->
                            folder.writeFile(script) {
                                javaClass.getResource("/scripts/$script").readText()
                            }
                        }
                }
            } ?: notifier.error { "No renderer found for $this" }
    }

    fun Presentation.renderPdf(config: Config) {
        val output = (config.output / id.id)
        val filename = "${id.id}.pdf"
        val content = cache.computeIfAbsent(Html to this) {
            findRenderer(Html, this)
                ?.render(this)
                ?: throw IllegalStateException("No HTML renderer found for $this")
        }
        notifier.time("Write to ${Styles.highlight(filename)}") {
            HtmlToPdf.htmlToPdf(sTitle, content, output.resolveAbsolutePath(filename))
        }
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