package io.github.ilaborie.slides2.kt.engine

import demo
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.devoxxFr19
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse.Companion.CanIUsePlugin
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet.Companion.TweetPlugin
import io.github.ilaborie.slides2.kt.jvm.jvmConfig
import webComponents


fun main() {
    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin, GridPlugin)
        .use(TweetPlugin, CanIUsePlugin)
        .use(
            PrismJsPlugin(showLines = false),
            RoughSvgPlugin
        )

//    val allThemes = Theme.all.values.toList()

//    val demoOut = SlideEngine.run(jvmConfig("presentations/samples"), demo, allThemes)
    val wcOut = SlideEngine.run(jvmConfig("presentations/WebComponents2019"), webComponents, listOf(devoxxFr19))

//    JvmFolder("public")
//        .writeTextFile("data.json") {
//            listOf(demoOut, wcOut).joinToString(", ", "[ ", "]") { it.json }
//        }
}
