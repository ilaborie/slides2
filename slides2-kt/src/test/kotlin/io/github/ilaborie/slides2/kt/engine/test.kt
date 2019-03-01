package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.SlideEngine.notifier
import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.contents.Quote
import io.github.ilaborie.slides2.kt.engine.contents.raw
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
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
            globalScripts += listOf("navigate.js", "toc.js")
        }


    val presentation = pres("Demo Presentation") {
        part("A part") {
            slide("A slide") { p { "lorem ipsum" } }
            roadmap("Roadmap")
        }
        part("Another part") {
            slide("Slide with Markdown", styles = setOf("two-columns")) {
                file("content/test.md")
            }
            slide("Slide 2") {
                p { "A simple list" }
                ul {
                    listOf(
                        "lorem".raw,
                        "ipsum".raw,
                        "dolor".raw,
                        "sit".raw,
                        "amet".raw
                    )
                }
            }
            slide("Slide 3") {
                p { "A simple list with steps" }
                ul(steps = true) {
                    listOf(
                        "lorem".raw,
                        "ipsum".raw,
                        "dolor".raw,
                        "sit".raw,
                        "amet".raw
                    )
                }
            }
            slide("Slide 4", styles = setOf("two-columns")) {
                markdown { "A simple list with `two-columns` class" }
                ul(steps = true) {
                    listOf(
                        "lorem".raw,
                        "ipsum".raw,
                        "dolor".raw,
                        "sit".raw,
                        "amet".raw
                    )
                }
            }
            slide("Slide 5") {
                p { "A simple ordered list" }
                ol(steps = true) {
                    listOf(
                        "lorem".raw,
                        "ipsum".raw,
                        "dolor".raw,
                        "sit".raw,
                        "amet".raw
                    )
                }
            }
            slide("Slide 6") { code("javascript"){
                """tocMenu.querySelectorAll("a")
    .forEach(a =>
        a.addEventListener('click', () =>
            document.getElementById("toc-toggle").checked = false));"""
            } }
        }
        part("Last part") {
            slide("Slide 1") { p { "lorem ipsum" } }
            slide("Slide 2") { link("http://www.google.com") { "Google".raw } }
            slide("Slide 3") { quote(author = "Anonymous") { "Plop, Plop, Plouf !".raw } }
            slide("End") { p { "lorem ipsum" } }
        }
    }

    with(SlideEngine) {
        presentation(config).renderHtml(config)
    }

}