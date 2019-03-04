package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.engine.*
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.extra.*
import io.github.ilaborie.slides2.kt.engine.plugins.ContentPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.*
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUseHtmlRenderer
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUseTextRenderer
import io.github.ilaborie.slides2.kt.jvm.extra.TweetHtmlRenderer
import io.github.ilaborie.slides2.kt.jvm.extra.TweetTextRenderer
import io.github.ilaborie.slides2.kt.jvm.tools.ScssToCss.scssFileToCss
import io.github.ilaborie.slides2.kt.term.Notifier.time
import io.github.ilaborie.slides2.kt.term.Styles

object SlideEngine {

    val rendererMap: MutableMap<String, MutableMap<RenderMode, Renderer<*>>> = mutableMapOf()
    private val contentPlugins: MutableList<ContentPlugin> = mutableListOf()

    private val cache: MutableMap<Pair<RenderMode, Presentation>, String> = mutableMapOf()

    val globalScripts: MutableList<Script> = mutableListOf()
    val globalStylesheets: MutableList<Stylesheet> = mutableListOf()

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
        registerRenderers(BarChartTextRenderer, BarChartHtmlRenderer)
        registerRenderers(InlineFigureTextRenderer, InlineFigureHtmlRenderer)
        // Extra with JVM
        registerRenderers(TweetTextRenderer, TweetHtmlRenderer)
        registerRenderers(CanIUseTextRenderer, CanIUseHtmlRenderer)

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

    fun asText(content: Content): String =
        findRenderer(Text, content)
            ?.render(content)
            ?.replace('\n', ' ')
            ?: throw IllegalStateException("No Text renderer found for $content")

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
                time("Write to ${Styles.highlight("themes")}") {
                    writePresentationStylesheets(config)
                }
                // Scripts
                time("Write ${Styles.highlight("Global scripts")}") {
                    writePresentationScripts(config)
                }
                PresentationOutputInstance(label = theme.name, path = "${id.id}/$filename")
            } ?: throw IllegalStateException("No Html renderer found for $this")

    private fun Presentation.writePresentationScripts(config: Config) {
        val folder = config.output / id.id
        globalScripts
            .filterNot { it.src.startsWith("http") }
            .forEach { script ->
                // lookup input
                if (config.input.exists(script.src)) {
                    folder.writeFile(script.src) {
                        config.input.readFileAsString(script.src)
                    }
                } else {
                    val res = javaClass.getResource("/scripts/${script.src}")
                    res?.also { r ->
                        folder.writeFile(script.src) {
                            r.readText()
                        }
                    }
                }
            }
    }

    private fun Presentation.writePresentationStylesheets(config: Config) {
        val folder = config.output / id.id
        val themeFile = "${theme.name}.css"
        folder.writeFile(themeFile) {
            theme.compiled
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
    }

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