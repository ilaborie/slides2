import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.contents.*

pres("Demo Presentation") {
    part("A part") {
        slide("A slide") { p { "lorem ipsum" } }
        roadmap("Roadmap")
    }
    part("Another part") {
        slide("Slide with Markdown", styles = setOf("two-columns")) { file("content/test.md") }
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
        slide("Slide 3", styles = setOf("two-columns")) {
            markdown { "A simple list with `two-columns` class" }
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
        slide("Slide 4") {
            p { "A simple ordered list" }
            ol {
                listOf(
                    "lorem".raw,
                    "ipsum".raw,
                    "dolor".raw,
                    "sit".raw,
                    "amet".raw
                )
            }
        }
        slide("Slide 5") { p { "lorem ipsum" } }
    }
    part("Last part") {
        slide("Slide 1") { p { "lorem ipsum" } }
        slide("End") { p { "lorem ipsum" } }
    }
}