package io.github.ilaborie.slides2.kt.engine

import demo
import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.base
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.devoxxFr19
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.mixit19
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.rivieraDev19
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.sunnyTech19
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.tlsJug
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.usePrismJs
import io.github.ilaborie.slides2.kt.jvm.jvmConfig
import webComponents


fun main() {

    // Configure engine
    SlideEngine
        .registerContentPlugin(CheckContentPlugin)
        .apply {
            globalScripts += listOf("navigate.js", "toc.js", "line-numbers.js")
            registerRenderer(usePrismJs())
        }

    run(demo, "presentations/samples" to "public", base, tlsJug, devoxxFr19, mixit19, sunnyTech19, rivieraDev19)
    run(webComponents, "presentations/WebComponents2019" to "public", devoxxFr19)
}

fun run(
    presentation: (Folder) -> Presentation,
    folders: Pair<String, String>,
    vararg themes: Theme
) {
    val (from, to) = folders
    val config = jvmConfig(from = from, to = to)
    val pres = presentation(config.input)
    Notifier.time("Generate all ${pres.sTitle}") {
        with(SlideEngine) {
            themes
                .map { pres.copy(theme = it) }
                .forEach { it.renderHtml(config) }
        }
    }
}