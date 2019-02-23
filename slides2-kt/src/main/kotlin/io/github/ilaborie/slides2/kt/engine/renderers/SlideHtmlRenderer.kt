package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Slide


object SlideHtmlRenderer : Renderer<Slide> {
    override val mode: RenderMode = Html

    override fun render(content: Slide): String =
        with(SlideEngine) {
            val classes = content.styles.joinToString(" ")
            val body = content.content.joinToString("\n") {
                render(mode, it)
            }

            """
                |<section id="${content.key}"${if (classes == "") "" else """ class="$classes""""}>
                |  <header>
                |  ${render(mode, content.title).prependIndent("  ")}
                |  </header>
                |  <nav>
                |    <a href="#previous" class="previous"></a>
                |  </nav>
                |  <div class="body">
                |${body.prependIndent("    ")}
                |  </div>
                |  <nav>
                |    <a href="#previous" class="next"></a>
                |  </nav>
                |  <footer></footer>
                |</section>
            """.trimMargin()
        }
}