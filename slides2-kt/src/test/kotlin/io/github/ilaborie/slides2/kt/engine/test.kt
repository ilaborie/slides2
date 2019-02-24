package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.SlideEngine.notifier
import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.jvm.JvmStopWatch
import java.io.File


fun main() {

    val config = Config(
        notifier = Notifier(JvmStopWatch),
        output = JvmFolder(File("src/main/web"), notifier = notifier)
    )

    // Configure engine
    SlideEngine
        .apply { notifier = config.notifier }
        .registerContentPlugin(CheckContentPlugin(config.notifier))

    val presentation = pres("Test") {
        part("A part") {
            slide("A slide") { p("lorem ipsum") }
            roadmap("Roadmap")
        }
        part("Another part") {
            slide("Slide 1") { p("lorem ipsum") }
            slide("Slide 2") { p("lorem ipsum") }
            slide("Slide 3") { p("lorem ipsum") }
            slide("Slide 4") { p("lorem ipsum") }
            slide("Slide 5") { p("lorem ipsum") }
        }
        part("Last part") {
            slide("Slide 1") { p("lorem ipsum") }
            slide("End") { p("lorem ipsum") }
        }
    }

    with(SlideEngine) {
        presentation.render(config)
    }

}