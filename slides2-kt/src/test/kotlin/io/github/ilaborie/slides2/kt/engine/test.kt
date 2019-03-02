package io.github.ilaborie.slides2.kt.engine

import demo
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.devoxxFr19
import io.github.ilaborie.slides2.kt.engine.extra.usePrismJs
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.jvm.jvmConfig
import io.github.ilaborie.slides2.kt.jvm.run
import webComponents


fun main() {
    SlideEngine
        .registerContentPlugin(CheckContentPlugin)
        .apply {
            globalScripts += listOf("navigate.js", "toc.js", "line-numbers.js")
            registerRenderer(usePrismJs())
        }

    val allThemes = Theme.all.values.toList()
    run(jvmConfig("presentations/samples"), demo, allThemes)
    run(jvmConfig("presentations/WebComponents2019"), webComponents, listOf(devoxxFr19))
}
