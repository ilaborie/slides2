package io.github.ilaborie.slides2.kt.engine

import demo
import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.SlideEngine.notifier
import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.base
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.devoxxFr19
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.mixit19
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.rivieraDev19
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.sunnyTech19
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.tlsJug
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.usePrismJs
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.jvm.JvmStopWatch
import webComponents
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

    val presentations = mapOf(
        demo to listOf(base, tlsJug, devoxxFr19, mixit19, sunnyTech19, rivieraDev19),
        webComponents to listOf(devoxxFr19)
    )

    with(SlideEngine) {
        presentations
            .flatMap { (pres, themes) ->
                themes.map {
                    pres(config).copy(theme = it)
                }
            }
            .forEach { it.renderHtml(config) }
    }

}