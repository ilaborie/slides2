package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.engine.*
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.extra.*
import io.github.ilaborie.slides2.kt.engine.plugins.ContentPlugin
import io.github.ilaborie.slides2.kt.engine.plugins.Plugin
import io.github.ilaborie.slides2.kt.engine.plugins.RendererPlugin
import io.github.ilaborie.slides2.kt.engine.plugins.WebPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.*
import io.github.ilaborie.slides2.kt.jvm.tools.ScssToCss.scssFileToCss
import io.github.ilaborie.slides2.kt.term.Notifier.time
import io.github.ilaborie.slides2.kt.term.Styles

object SlideEngine {

    private val rendererMap: MutableMap<String, MutableMap<RenderMode, Renderer<*>>> = mutableMapOf()
    private val contentPlugins: MutableList<ContentPlugin> = mutableListOf()

    private val cache: MutableMap<Pair<RenderMode, Presentation>, String> = mutableMapOf()

    private val globalScripts: MutableList<Script> = mutableListOf()
    private val globalStylesheets: MutableList<Stylesheet> = mutableListOf()

    val scripts: List<Script>
        get() = globalScripts
    val stylesheets: List<Stylesheet>
        get() = globalStylesheets

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

        // Presentation, Part, Slide
        registerRenderers(PresentationHtmlRenderer)
        registerRenderers(SlideHtmlRenderer)
    }

    private fun <T : Content> registerRenderer(renderer: Renderer<*>, clazz: Class<T>): SlideEngine {
        val map = rendererMap.computeIfAbsent(clazz.name) { mutableMapOf() }
        map[renderer.mode] = renderer
        return this
    }

    private inline fun <reified T : Content> registerRenderers(vararg renderer: Renderer<T>): SlideEngine {
        renderer.forEach { registerRenderer(it, T::class.java) }
        return this
    }

    fun use(vararg plugins: Plugin): SlideEngine {
        plugins.forEach { plugin ->
            when (plugin) {
                is ContentPlugin     ->
                    contentPlugins += plugin
                is WebPlugin         -> {
                    globalScripts += plugin.scripts()
                    globalStylesheets += plugin.stylesheets()
                }
                is RendererPlugin<*> ->
                    plugin.renderers().forEach {
                        registerRenderer(it, plugin.clazz)
                    }
            }
        }
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

    fun Presentation.renderHtml(config: Config, metadata: Map<String, String> = emptyMap()): PresentationOutputInstance =
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
                PresentationOutputInstance(theme.name, id.id, metadata)
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