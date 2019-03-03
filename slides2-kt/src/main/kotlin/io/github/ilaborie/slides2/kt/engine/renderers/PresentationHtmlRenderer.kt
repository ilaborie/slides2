package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Stylesheet

open class PresentationHtmlRenderer : Renderer<Presentation> {
    override val mode: RenderMode = Html

    open fun head(presentation: Presentation): String {
        val scripts = SlideEngine.globalScripts
            .filter { it.module }
            .joinToString("\n") { it.asHtml() }

        val innerStyle = (listOf(presentation.theme.name, presentation.extraStyle))
            .filterNotNull()
            .map { Stylesheet("./$it.css") }

        val stylesheets = (SlideEngine.globalStylesheets + innerStyle)
            .joinToString("\n") { it.asHtml() }

        return """<head>
                |  <meta charset="utf-8">
                |  <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">
                |${stylesheets.prependIndent("  ")}
                |${scripts.prependIndent("  ")}
                |  <title>${presentation.sTitle}</title>
                |</head>""".trimMargin()
    }

    open fun beforeMain(presentation: Presentation): String =
        ""
//        """<header class="no-print">
//            |  ${SlideEngine.render(mode, presentation.title)}
//            |  <a href="#${presentation.coverSlide.id.id}">📺</a>
//            |</header>""".trimMargin()

    open fun afterMain(presentation: Presentation): String =
        SlideEngine.globalScripts
            .filterNot { it.module }
            .joinToString("\n") { it.asHtml() }

    override fun render(content: Presentation): String =
        with(SlideEngine) {
            val body = content.allSlides.joinToString("\n") {
                render(mode, it)
            }

            """<!doctype html>
            |<html lang="${content.lang}">
            |${head(content)}
            |<body class="${content.theme.name}">
            |${beforeMain(content).prependIndent("  ")}
            |  <main>
            |$body
            |  </main>
            |${afterMain(content).prependIndent("  ")}
            |</body>
            |</html>""".trimMargin()
        }
}