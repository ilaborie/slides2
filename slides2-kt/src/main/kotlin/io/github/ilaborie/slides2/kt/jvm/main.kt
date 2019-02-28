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
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
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

    private val output by option("-o", "--output", help = "the output folder, (default: `build`)")
        .default("build")

    private val pdf by option("--pdf", help = "Also render as PDF, (default: false)").flag(default = false)

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

        val mgr = ScriptEngineManager()
        val engine = mgr.getEngineByExtension("kts")

        val eval = engine.eval(scriptFile.path)
        val presentation =
            (eval as? Presentation) ?: throw IllegalStateException("Expected a Presentation, got $eval")

        val config = Config(
            notifier = log,
            output = JvmFolder(File(output), notifier = notifier),
            input = JvmFolder(scriptFile.parentFile, notifier = notifier)
        )

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
