import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.dsl.raw
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Danger
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Info
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Tips
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Warning


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
            p("A simple list with steps")
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
            p("A simple ordered list")
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
            p(
                """Lorem ipsum dolor sit amet consectetur adipisicing elit.
                |Repellendus neque reiciendis quaerat natus perspiciatis temporibus aut laboriosam,
                |itaque est consectetur.
                |Dolorem cum eaque odit voluptatibus laboriosam vero modi cumque deserunt?
                |""".trimMargin()
            )
        }
        slide("Slide Link") {
            link("http://www.google.com") { "Google".raw }
        }
        slide("Slide Quote") {
            quote(author = "Anonymous") { html { "Plop, Plop, Plouf !" } }
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
            notice(Tips, "block tips") { "Lorem ipsum dolor sit amet.".raw }
            notice(Info, "block info") { "Lorem ipsum dolor sit amet.".raw }
            notice(Warning, "block warn") { "Lorem ipsum dolor sit amet.".raw }
            notice(Danger, "block danger") { "Lorem ipsum dolor sit amet.".raw }
        }
        slide("Slide Figure") {
            figure("content/figure.png", title = "A chart")
        }
    }
    part("Last part") {
        slide("End") { p("lorem ipsum") }
    }
}
