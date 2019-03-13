package io.github.ilaborie.slides2.kt.jvm

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.file
import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.PresentationDsl
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse.Companion.CanIUsePlugin
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet.Companion.TweetPlugin
import io.github.ilaborie.slides2.kt.term.Notifier
import io.github.ilaborie.slides2.kt.utils.Try
import java.io.File
import java.io.InputStreamReader
import javax.script.ScriptEngineManager


fun main(args: Array<String>) =
    Slides
        .subcommands(
            BuildSlides,
            ListThemes,
            ListPlugins
        )
        .main(args)

object Slides : CliktCommand() {
    override fun run() = Unit
}

object ListThemes : CliktCommand(name = "themes", help = "list available themes") {
    override fun run() {
        Theme.all
            .forEach { _, theme -> println(theme) }
    }
}

object ListPlugins : CliktCommand(name = "plugins", help = "list available plugins") {
    override fun run() {
        allPlugins
            .forEach { name, plugin -> println("$name: ${plugin.name}") }
    }
}

private val allPlugins: Map<String, Plugin> = mapOf(
    "check" to CheckContentPlugin,
    "toc" to TocPlugin,
    "grid" to GridPlugin,
    "navigate" to NavigatePlugin,
    "tweet" to TweetPlugin,
    "caniuse" to CanIUsePlugin,
    "prism" to PrismJsPlugin(showLines = false),
    "rough-svg" to RoughSvgPlugin
)

object BuildSlides : CliktCommand(name = "build", help = "Build slides") {

    private val script by argument(help = "the Kotlin script file (*.kts)").file(exists = true)

    private val output by option("-o", "--output", help = "the output folder")
        .defaultWithMessage("public")

    private val themes by option("-t", "--themes", help = "list of themes, (default: `base`)")
        .multiple(listOf("base"))

    private val plugins: List<String> by option("-p", "--plugin", help = "Toggle plugins")
        .multiple(listOf())

    private val verbose by option("-v", "--verbose", help = "verbose mode").flag(default = false)
    private val watch by option("-w", "--watch", help = "watch mode").flag(default = false)

    private val engine by lazy {
        ScriptEngineManager().getEngineByExtension("kts")
    }

    override fun run() {
        val scriptFile = script
        if (!scriptFile.exists()) {
            error { "File '$scriptFile' does not exists !" }
        }
        if (scriptFile.extension != "kts") {
            error { "File '$scriptFile' is not a kotlin script file (*.kts) !" }
        }

        Notifier.verbose = verbose

        // Configure engine
        plugins
            .map {
                allPlugins[it]
                    ?: throw IllegalArgumentException(
                        "No plugin for $it, existing plugins: ${allPlugins.keys.sorted().joinToString(", ")}"
                    )
            }
            .forEach { SlideEngine.use(it) }

        build(scriptFile)
    }

    private fun build(scriptFile: File) {
        val allThemes = themes.map { Theme[it] }
        val config = Config(
            input = JvmFolder(scriptFile.parentFile),
            output = JvmFolder(File(output))
        )

        scriptFile.reader()
            .use { buildPresentation(it) }
            .map { SlideEngine.run(config, it, allThemes) }
            .doOnError { Notifier.error(label = "💣 ", cause = it) { "Oops!" } }
            .unwrap()
    }

    private fun buildPresentation(it: InputStreamReader): Try<PresentationDsl> {
        return Try {
            val eval = engine.eval(it)
            eval as? PresentationDsl
                ?: throw IllegalStateException("Expected a Presentation, got $eval")
        }
    }
}

//fun <EachT : Any, ValueT> NullableOption<EachT, ValueT>.requiredWithMessage()
//        : OptionWithValues<EachT, EachT, ValueT> =
//    copy(
//        transformValue,
//        transformEach,
//        { it.lastOrNull() ?: throw MissingParameter(option) },
//        help = "(required) $help"
//    )

fun <EachT : Any, ValueT> NullableOption<EachT, ValueT>.defaultWithMessage(value: EachT)
        : OptionWithValues<EachT, EachT, ValueT> =
    transformAll { it.lastOrNull() ?: value }
        .run { copy(transformValue, transformEach, transformAll, help = "$help, (default: $value)") }
