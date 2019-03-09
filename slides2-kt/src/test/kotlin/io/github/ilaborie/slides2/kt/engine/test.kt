package io.github.ilaborie.slides2.kt.engine

import demo
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.devoxxFr19
import io.github.ilaborie.slides2.kt.engine.extra.PrismJsPlugin
import io.github.ilaborie.slides2.kt.engine.extra.RoughSvgPlugin
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.engine.plugins.NavigatePlugin
import io.github.ilaborie.slides2.kt.engine.plugins.TocPlugin
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse.Companion.CanIUsePlugin
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet.Companion.TweetPlugin
import io.github.ilaborie.slides2.kt.jvm.jvmConfig
import io.github.ilaborie.slides2.kt.jvm.run
import webComponents


fun main() {
    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin)
        .use(TweetPlugin, CanIUsePlugin)
        .use(PrismJsPlugin(showLines = false), RoughSvgPlugin)

    val allThemes = Theme.all.values.toList()

    val demoOut = run(jvmConfig("presentations/samples"), demo, allThemes)
    val wcOut = run(jvmConfig("presentations/WebComponents2019"), webComponents, listOf(devoxxFr19))

    JvmFolder("public")
        .writeFile("data.json") {
            listOf(demoOut, wcOut).joinToString(", ", "[ ", "]") { it.json }
        }
}
