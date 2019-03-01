package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.SlideEngine.notifier
import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Danger
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Info
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Tips
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Warning
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
    // https://cdnjs.cloudflare.com/ajax/libs/prism/1.15.0/prism.min.js
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
            slide("Slide List") {
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
            slide("Slide List steps") {
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
            slide("Slide List 2 columns", styles = setOf("two-columns")) {
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
            slide("Slide Ordered list") {
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
        }
        part("Some Contents") {
            slide("Slide Paragraph") {
                p {
                    """Lorem ipsum dolor sit amet consectetur adipisicing elit.
                |Repellendus neque reiciendis quaerat natus perspiciatis temporibus aut laboriosam,
                |itaque est consectetur.
                |Dolorem cum eaque odit voluptatibus laboriosam vero modi cumque deserunt?
                |""".trimMargin()
                }
            }
            slide("Slide Link") {
                link("http://www.google.com") { "Google".raw }
            }
            slide("Slide Quote") {
                quote(author = "Anonymous") { "Plop, Plop, Plouf !".raw }
            }
            slide("Slide Code") {
                code("javascript") {
                    """tocMenu.querySelectorAll("a")
                        |  .forEach(a =>
                        |    a.addEventListener('click', () =>
                        |      $("#toc-toggle").checked = false));""".trimMargin()
                }
            }
            slide("Slide Source from file") {
                sourceCode("content/plop.js")
            }
            slide("Slide Notice") {
                notice(Tips) { "block tips".raw }
                notice(Info) { "block info".raw }
                notice(Warning) { "block warn".raw }
                notice(Danger) { "block danger".raw }
            }
            slide("Slide Figure") {
                figure("content/figure.png", title = "A chart")
            }
        }
        part("Last part") {
            slide("End") { p { "lorem ipsum" } }
        }
    }

    with(SlideEngine) {
        presentation(config).renderHtml(config)
    }

}