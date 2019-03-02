package io.github.ilaborie.slides2.kt.jvm

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.SlideEngine.applyPlugins
import io.github.ilaborie.slides2.kt.dsl.PresentationDsl
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.extra.usePrismJs
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.term.Notifier
import io.github.ilaborie.slides2.kt.term.Notifier.time
import io.github.ilaborie.slides2.kt.utils.Try
import java.io.File
import javax.script.ScriptEngineManager


fun main(args: Array<String>) =
    Slides.main(args)

object Slides : CliktCommand(name = "build", help = "Build slides") {

    private val script by argument("script", help = "the Kotlin script file (*.kts)")

    private val output by option("-o", "--output", help = "the output folder, (default: `public`)")
        .default("public")

    private val themes by option("-t", "--themes", help = "list of themes, (default: `base`)")
        .multiple(listOf("base"))

    private val prism by option("--prism", help = "Toggle the PrismJs syntax coloring")
        .flag()

    private val engine by lazy {
        ScriptEngineManager().getEngineByExtension("kts")
    }

    override fun run() {
        val scriptFile = File(script)
        if (!scriptFile.exists()) {
            error { "File '$scriptFile' does not exists !" }
        }
        if (scriptFile.extension != "kts") {
            error { "File '$scriptFile' is not a kotlin script file (*.kts) !" }
        }


        // Configure engine
        SlideEngine
            .registerContentPlugin(CheckContentPlugin)
            .apply {
                globalScripts += listOf("navigate.js", "toc.js")
                if (prism) {
                    globalScripts += "line-numbers.js"
                    registerRenderer(usePrismJs())
                }
            }

        val pres = scriptFile.reader()
            .use {
                Try {
                    val eval = engine.eval(it)
                    eval as? PresentationDsl
                        ?: throw IllegalStateException("Expected a Presentation, got $eval")
                }
            }

        val allThemes = themes.map { Theme[it] }
        val config = Config(
            input = JvmFolder(scriptFile.parentFile),
            output = JvmFolder(File(output))
        )
        pres
            .map { run(config, it, allThemes) }
            .recover { Notifier.error(cause = it) { "Oops!" } }
    }
}

fun run(config: Config, presentation: PresentationDsl, themes: List<Theme>) {
    val pres = applyPlugins { presentation(config.input) }
    time("Generate all `${pres.sTitle}`") {
        with(SlideEngine) {
            themes
                .map { pres.copy(theme = it) }
                .forEach { it.renderHtml(config) }
        }
    }
}
