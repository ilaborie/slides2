package io.github.ilaborie.slides2.kt.jvm

import com.github.ajalt.clikt.core.CliktCommand
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
import javax.script.ScriptEngineManager


fun main(args: Array<String>) =
    Slides.main(args)

// TODO list theme
// TODO list plugin
// TODO build
// TODO watch


private val allPlugins: Map<String, Plugin> = mapOf(
    "check" to CheckContentPlugin,
    "toc" to TocPlugin,
    "navigate" to NavigatePlugin,
    "tweet" to TweetPlugin,
    "caniuse" to CanIUsePlugin,
    "prism" to PrismJsPlugin(showLines = false),
    "rough-svg" to RoughSvgPlugin
)

object Slides : CliktCommand(name = "build", help = "Build slides") {

    private val script by argument(help = "the Kotlin script file (*.kts)").file(exists = true)

    private val output by option("-o", "--output", help = "the output folder")
        .defaultWithMessage("public")

    private val themes by option("-t", "--themes", help = "list of themes, (default: `base`)")
        .multiple(listOf("base"))

    private val plugins: List<String> by option("-p", "--plugin", help = "Toggle plugins")
        .multiple(listOf("check", "toc", "navigate", "tweet", "caniuse", "prism", "rough-svg"))

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

        // Configure engine
        plugins
            .map {
                allPlugins[it] ?: throw IllegalArgumentException(
                    "No plugin for $it, existing plugins: ${allPlugins.keys.sorted()
                        .joinToString(", ")}"
                )
            }
            .forEach { SlideEngine.use(it) }

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
            .map { SlideEngine.run(config, it, allThemes) }
            .recover {
                Notifier.error(cause = it) { "Oops!" }
                throw it
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
        .run {
            copy(transformValue, transformEach, transformAll, help = "$help, (default: $value)")
        }
