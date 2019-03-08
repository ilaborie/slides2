package io.github.ilaborie.slides2.kt.engine

import demo
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.devoxxFr19
import io.github.ilaborie.slides2.kt.engine.extra.usePrismJs
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.jvm.jvmConfig
import io.github.ilaborie.slides2.kt.jvm.run
import webComponents


fun main() {
    SlideEngine
        .registerContentPlugin(CheckContentPlugin)
        .apply {
            globalScripts += listOf(
                Script("./navigate.js"),
                Script("./toc.js"),
                Script("./line-numbers.js"),
                Script(
                    "https://cdnjs.cloudflare.com/ajax/libs/rough.js/3.0.0/rough.js",
                    async = false,
                    module = false,
                    defer = false
                ),
                Script("./rough-svg.js", async = false, module = false, defer = false)
            )

            usePrismJs(showLines = false)
        }
    val allThemes = Theme.all.values.toList()

    val demoOut = run(jvmConfig("presentations/samples"), demo, allThemes)
    val wcOut = run(jvmConfig("presentations/WebComponents2019"), webComponents, listOf(devoxxFr19))

    JvmFolder("public")
        .writeFile("data.json") {
            listOf(demoOut, wcOut).joinToString(", ", "[ ", "]") { it.json }
        }
}
