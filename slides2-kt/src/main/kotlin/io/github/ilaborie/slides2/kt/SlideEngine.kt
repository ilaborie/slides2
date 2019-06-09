package io.github.ilaborie.slides2.kt

import io.github.ilaborie.slides2.kt.dsl.PresentationDsl
import io.github.ilaborie.slides2.kt.engine.*
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.contents.*
import io.github.ilaborie.slides2.kt.engine.plugins.ContentPlugin
import io.github.ilaborie.slides2.kt.engine.plugins.Plugin
import io.github.ilaborie.slides2.kt.engine.plugins.RendererPlugin
import io.github.ilaborie.slides2.kt.engine.plugins.WebPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.*
import io.github.ilaborie.slides2.kt.jvm.tools.Natives.scssFileToCss
import io.github.ilaborie.slides2.kt.term.Notifier.info
import io.github.ilaborie.slides2.kt.term.Notifier.time
import io.github.ilaborie.slides2.kt.term.Styles.highlight

object SlideEngine {

    private val rendererMap: MutableMap<String, MutableMap<RenderMode, Renderer<*>>> = mutableMapOf()
    private val contentPlugins: MutableList<ContentPlugin> = mutableListOf()

    private val cache: MutableMap<Pair<RenderMode, Presentation>, String> = mutableMapOf()

    private val globalScripts: MutableList<Script> = mutableListOf()
    val scripts: List<Script>
        get() = globalScripts.distinct()

    private val globalStylesheets: MutableList<Stylesheet> = mutableListOf()
    val stylesheets: List<Stylesheet>
        get() = globalStylesheets.distinct()

    init {
        // Base
        registerRenderers(TextTextRenderer, TextHtmlRenderer)
        registerRenderers(MarkdownTextRenderer, MarkdownHtmlRenderer)
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
        registerRenderers(DefinitionsTextRender, DefinitionsHtmlRender)
        registerRenderers(JmhBenchmarkTextRenderer, JmhBenchmarkHtmlRenderer)

        // Extra
        registerRenderers(SpeakerTextRenderer, SpeakerHtmlRenderer)
        registerRenderers(BarChartTextRenderer, BarChartHtmlRenderer)
        registerRenderers(InlineFigureTextRenderer, InlineFigureHtmlRenderer)

        // Presentation, Part, Slide
        registerRenderers(PresentationTextRenderer, PresentationHtmlRenderer)
        registerRenderers(SlideTextRenderer, SlideHtmlRenderer)
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
            info("🧩: plugin") { "using ${plugin.name}" }
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

    private fun Presentation.renderHtml(config: Config): (Map<String, String>) -> PresentationOutputInstance =
        { metadata ->
            findRenderer(Html, this)
                ?.let { renderer ->
                    val folder = config.output / id.id

                    // Slides
                    val filename = "index-${theme.name}.html"
                    info("⚙️") { highlight(filename) }
                    folder.writeTextFile(filename) {
                        cache.computeIfAbsent(Html to this) {
                            renderer.render(this)
                        }
                    }

                    writePresentationStylesheets(config)
                    writePresentationScripts(config)

                    // favicon
                    if (favicon) {
                        info("⚙️") { highlight("favicon") }
                        config.input.copyOrUpdate("favicon.ico", folder)
                    }

                    PresentationOutputInstance(theme.name, id.id, metadata)
                } ?: throw IllegalStateException("No Html renderer found for $this")
        }

    private fun Presentation.renderText(config: Config) {
        findRenderer(Text, this)
            ?.let { renderer ->
                val folder = config.output / id.id
                // Slides
                val filename = "${id.id}.md"
                info("⚙️") { highlight(filename) }
                folder.writeTextFile(filename) {
                    cache.computeIfAbsent(Text to this) {
                        renderer.render(this)
                    }
                }
            } ?: throw IllegalStateException("No Text renderer found for $this")
    }

    private fun Presentation.writePresentationScripts(config: Config) {
        val folder = config.output / id.id

        val scriptToCopy = globalScripts.filterIsInstance<Script.Companion.BaseScript>()

        info("⚙️") { "global script ${scriptToCopy.joinToString(", ") { highlight(it.localSrc) }}" }
        scriptToCopy
            .filter { it.cacheLocal }
            .forEach { script ->
                // lookup input
                when {
                    script.src.startsWith("http")   ->
                        folder.writeUrlContent(script.src)
                    config.input.exists(script.src) ->
                        config.input.copyOrUpdate(script.src, folder)
                    else                            ->
                        javaClass.getResource("/scripts/${script.src}")
                            ?.also { r ->
                                folder.writeTextFile(script.src) {
                                    r.readText()
                                }
                            }
                }
            }
    }

    private fun Presentation.writePresentationStylesheets(config: Config) {
        val folder = config.output / id.id
        val themeFile = "${theme.name}.css"

        info("⚙️") { "main theme ${highlight(theme.name)}" }
        folder.writeTextFile(themeFile) {
            theme.compiled
        }
        if (extraStyle != null) {
            val outputFilename = "$extraStyle.css"
            info("⚙️") { "extra style ${highlight(outputFilename)}" }
            folder.writeTextFile(outputFilename) {
                val path = config.input.resolveAbsolutePath("$extraStyle.scss")
                scssFileToCss(path)
            }
        }
        if (globalStylesheets.isNotEmpty()) {
            info("⚙️") { "global stylesheet ${globalStylesheets.joinToString(", ") { highlight(it.localHref) }}" }
            globalStylesheets.forEach {
                (config.output / id.id).writeUrlContent(it.href)
            }
        }
    }

    fun run(
        config: Config,
        presentation: PresentationDsl,
        themes: List<Theme>,
        metadata: (Theme) -> Map<String, String> = { emptyMap() }
    ): PresentationOutput {
        val pres = plugContent(presentation(config.input)) as Presentation
        val instances = time("Generate all `${pres.sTitle}`") {
            with(SlideEngine) {
                pres.renderText(config)
                if (config.notes != null) renderNotes(pres, config.output, config.notes)
                themes
                    .map {
                        info("🎨: theme") { "using ${it.name}" }
                        pres.copy(theme = it)
                    }
                    .map { it.renderHtml(config)(metadata(it.theme)) }
            }
        }
        return PresentationOutput(pres.sTitle, instances)
    }

    private fun renderNotes(pres: Presentation, output: Folder, notes: String): Unit =
        (output / pres.id.id).writeTextFile(notes) {
            info("📝: notes") { "Generate notes to  ${highlight(notes)}" }
            with(SlideEngine) {
                pres.allSlides
                    .asSequence()
                    .mapIndexed { i, slide ->
                        val prefix = when {
                            "cover" in slide.classes -> "#"
                            "part" in slide.classes  -> "##"
                            else                     -> "###"
                        }
                        """$prefix [${i + 1}] ${render(Text, slide.title)}
                      |${slide.notes ?: "Pas de notes"}
                      |---
                      |""".trimMargin()
                    }.joinToString("\n")
            }
        }


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