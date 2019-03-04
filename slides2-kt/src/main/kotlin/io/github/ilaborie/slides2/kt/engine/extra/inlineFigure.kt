package io.github.ilaborie.slides2.kt.engine.extra

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text


data class InlineFigure(val title: String, val svg: String, val copyright: Content?) : Content


fun ContainerBuilder.inlineFigure(svgPath: String, title: String, copyrightBlock: Content? = null) {
    content.add {
        val svg = input.readFileAsString(svgPath)
        InlineFigure(svg = svg, title = title, copyright = copyrightBlock)
    }
}

object InlineFigureHtmlRenderer : Renderer<InlineFigure> {
    override val mode: RenderMode = Html

    override fun render(content: InlineFigure): String =
        with(SlideEngine) {
            val copyright = content.copyright?.let {
                """<div class="copyright">
                  |${render(mode, it).prependIndent("    ")}
                  |</div>""".trimMargin()
            } ?: ""

            """<figure>
                |${content.svg}
                |$copyright
                |<figcaption>${content.title}</figcaption>
                |</figure>""".trimMargin()
        }
}


object InlineFigureTextRenderer : Renderer<InlineFigure> {
    override val mode: RenderMode = Text

    override fun render(content: InlineFigure): String =
        content.title
}
