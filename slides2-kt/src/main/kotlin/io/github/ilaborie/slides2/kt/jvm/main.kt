package io.github.ilaborie.slides2.kt.jvm

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.SlideEngine.notifier
import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.dsl.ConfigToPresentation
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.utils.Try
import java.io.File
import javax.script.ScriptEngineManager


fun main(args: Array<String>) = Slides()
    .subcommands(Build(), Watch())
    .main(args)

class Slides : CliktCommand() {
    override fun run() = Unit
}

class Build : CliktCommand(help = "Build slides") {

    private val script by argument("script", help = "the Kotlin script file (*.kts)")

    private val output by option("-o", "--output", help = "the output folder, (default: `public`)")
        .default("public")

    private val pdf by option("--pdf", help = "Also render as PDF, (default: false)").flag(default = false)
    
    private val engine by lazy {
        ScriptEngineManager().getEngineByExtension("kts")
    }

    override fun run() {
        val log = Notifier(JvmStopWatch)

        val scriptFile = File(script)
        if (!scriptFile.exists()) {
            notifier.error { "File '$scriptFile' does not exists !" }
            return
        }
        if (scriptFile.extension != "kts") {
            notifier.error { "File '$scriptFile' is not a kotlin script file (*.kts) !" }
            return
        }

        val config = Config(
            notifier = log,
            output = JvmFolder(File(output), notifier = notifier),
            input = JvmFolder(scriptFile.parentFile, notifier = notifier)
        )

        val presentation = scriptFile.reader().use {
            Try {
                val eval = engine.eval(it)
                (eval as? ConfigToPresentation)
                    ?.let { it(config) }
                    ?: throw IllegalStateException("Expected a Presentation, got $eval")
            }.unwrap()
        }

        // Configure engine
        SlideEngine
            .apply { notifier = config.notifier }
            .registerContentPlugin(CheckContentPlugin(config.notifier))

        with(SlideEngine) {
            presentation.renderHtml(config)
            if (pdf) {
                presentation.renderPdf(config)
            }
        }
    }
}

class Watch : CliktCommand(help = "Build and Watch slides") {
    override fun run() {
        echo("TODO")
    }
}
