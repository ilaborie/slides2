import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Danger
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Info
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Tips
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Warning
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse.Companion.CanIUsePlugin
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet.Companion.TweetPlugin
import io.github.ilaborie.slides2.kt.jvm.jvmConfig

fun main() {
    val allThemes = Theme.all.values.toList()
    val config = jvmConfig("presentations/samples")

    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin, GridPlugin)
        .use(TweetPlugin, CanIUsePlugin)
        .use(PrismJsPlugin(showLines = true, languages = listOf("typescript")))
        .run(config, demo, allThemes)
}

val demo = pres("Demo Presentation", extraStyle = "demo") {
    part("A part") {
        slide("A slide") { p("lorem ipsum") }
        roadmap("Roadmap")
    }
    part("Another part") {
        slide("Slide with Markdown", styles = setOf("two-columns")) {
            file("content/test.md")
        }
        slide("Slide List") {
            p("A simple list")
            ul(classes = setOf("bullet")) {
                html { "lorem" }
                html { "ipsum" }
                html { "dolor" }
                html { "sit" }
                html { "amet" }
            }
        }
        slide("Slide List steps") {
            p("A simple list with steps")
            ul(steps = true, classes = setOf("bullet")) {
                html { "lorem" }
                html { "ipsum" }
                html { "dolor" }
                html { "sit" }
                html { "amet" }
            }
        }
        slide("Slide List 2 columns", styles = setOf("two-columns")) {
            markdown { "A simple list with `two-columns` class" }
            ul(steps = true, classes = setOf("bullet")) {
                html { "lorem" }
                html { "ipsum" }
                html { "dolor" }
                html { "sit" }
                html { "amet" }
            }
        }
        slide("Slide Ordered list") {
            p("A simple ordered list")
            ol(steps = true, classes = setOf("bullet")) {
                html { "lorem" }
                html { "ipsum" }
                html { "dolor" }
                html { "sit" }
                html { "amet" }
            }
        }
        slide("Slide Inline list") {
            p("An inline list")
            ul(steps = true, classes = setOf("list-inline")) {
                html { "lorem" }
                html { "ipsum" }
                html { "dolor" }
                html { "sit" }
                html { "amet" }
            }
        }
    }
    part("Some Contents") {
        slide("Slide Paragraph") {
            p(
                """Lorem ipsum dolor sit amet consectetur adipisicing elit.
                |Repellendus neque reiciendis quaerat natus perspiciatis temporibus aut laboriosam,
                |itaque est consectetur.
                |Dolorem cum eaque odit voluptatibus laboriosam vero modi cumque deserunt?
                |""".trimMargin()
            )
        }
        slide("Slide Link") {
            link("http://www.google.com", "Google")
        }
        slide("Slide Quote") {
            quote("Plop, Plop, Plouf !", author = "Anonymous")
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
            sourceCode("content/rough-svg.js")
        }
        slide("Slide Notice") {
            notice(Tips, "block tips") { html { "Lorem ipsum dolor sit amet." } }
            notice(Info, "block info") { html { "Lorem ipsum dolor sit amet." } }
            notice(Warning, "block warn") { html { "Lorem ipsum dolor sit amet." } }
            notice(Danger, "block danger") { html { "Lorem ipsum dolor sit amet." } }
        }
        slide("Slide Figure") {
            figure("content/figure.png", title = "A chart")
        }
    }
    part("Last part") {
        slide("End") { p("lorem ipsum") }
    }
}
