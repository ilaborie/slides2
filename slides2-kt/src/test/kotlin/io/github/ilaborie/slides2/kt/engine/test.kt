package io.github.ilaborie.slides2.kt.engine

import demo
import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.SlideEngine.notifier
import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.usePrismJs
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.jvm.JvmStopWatch
import java.io.File


fun main() {

    val config = Config(
        notifier = Notifier(JvmStopWatch),
        output = JvmFolder(File("public"), notifier = notifier),
        input = JvmFolder(File("presentations/samples"), notifier = notifier)
    )

    // Configure engine
    SlideEngine
        .registerContentPlugin(CheckContentPlugin(config.notifier))
        .apply {
            notifier = config.notifier
            globalScripts += listOf("navigate.js", "toc.js", "line-numbers.js")
            registerRenderer(usePrismJs())
        }

    val presentations = listOf(demo)

    with(SlideEngine) {
        presentations
            .map { it(config) }
            .forEach { it.renderHtml(config) }
    }

}