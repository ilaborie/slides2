package io.github.ilaborie.slides2.kt.engine.contents

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.engine.ContainerContent
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.renderers.asHtmlClass
import io.github.ilaborie.slides2.kt.jvm.asKey


data class InlineFigure(
    val folder: Folder,
    val svgPath: String,
    val title: String,
    val copyright: Content?,
    override val classes: Set<String>
) : ContainerContent {

    override val inner: List<Content> =
        copyright?.let { listOf(it) } ?: emptyList()

    val svg: String by lazy {
        folder.readFileAsString(svgPath)
    }
    val xref: String by lazy {
        svgPath.asKey()
    }

}


fun ContainerBuilder.inlineFigure(
    svgPath: String,
    title: String,
    copyrightBlock: Content? = null,
    classes: Set<String> = emptySet()
) {
    content.add {
        InlineFigure(
            folder = input,
            svgPath = svgPath,
            title = title,
            copyright = copyrightBlock,
            classes = classes
        )
    }
}

object InlineFigureHtmlRenderer : Renderer<InlineFigure> {
    override val mode: RenderMode = Html

    override fun render(content: InlineFigure): String =
        with(SlideEngine) {
            val copyright = content.copyright?.let {
                """<div class="copyright">
                  |${render(mode, it)}
                  |</div>""".trimMargin()
            } ?: ""

            """<figure${content.classes.asHtmlClass}>
                |<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                |  <use xlink:href="#${content.xref}"/>
                |</svg>
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
