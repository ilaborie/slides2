package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.Stylesheet
import io.github.ilaborie.slides2.kt.engine.contents.InlineFigure

object PresentationHtmlRenderer : Renderer<Presentation> {
    override val mode: RenderMode = Html

    private fun head(presentation: Presentation): String {
        val scripts = SlideEngine.scripts
            .filter { it.module }
            .joinToString("\n") { it.asHtml() }

        val innerStyle = (listOf(presentation.theme.name, presentation.extraStyle))
            .filterNotNull()
            .map { Stylesheet("$it.css") }

        val stylesheets = (SlideEngine.stylesheets + innerStyle)
            .joinToString("\n") { it.asHtml() }

        return """<head>
                |  <meta charset="utf-8">
                |  <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">
                |${stylesheets.prependIndent("  ")}
                |${scripts.prependIndent("  ")}
                |  <title>${presentation.sTitle}</title>
                |</head>""".trimMargin()
    }

    private fun beforeMain(presentation: Presentation): String {

        // Inline SVG
        val inlineFigureSvg = presentation.flatten()
            .filterIsInstance<InlineFigure>()
            .distinctBy { it.xref }
            .map { inlineFigure ->
                inlineFigure.svg
                    .replace("<svg xmlns=\"http://www.w3.org/2000/svg\"", "\t<symbol id=\"${inlineFigure.xref}\"")
                    .replace("</svg>", "\t</symbol>")
            }.toList()

        val innerSvg = if (inlineFigureSvg.isEmpty()) "" else {
            inlineFigureSvg.joinToString(
                "\n",
                prefix = """<svg xmlns="http://www.w3.org/2000/svg" width="0" height="0" class="visually-hidden">""",
                postfix = "</svg>"
            )
        }

        val tocMenu = presentation.allSlides.joinToString("\n") { slide ->
            val classes = when {
                slide.isCover -> " class=\"cover\""
                slide.isPart  -> " class=\"part\""
                else          -> ""
            }
            """<li$classes><a href="#${slide.id.id}">$slide</a></li>"""
        }

        // Navigation & Grid
        return """$innerSvg
              |<nav class="toc-menu no-print">
              |  <label for="tocGrid" class="grid" style="display:none;"></label>
              |  <input id="tocToggle" type="checkbox" class="visually-hidden">
              |  <label for="tocToggle" class="toggle"></label>
              |  <ul>$tocMenu</ul>
              |</nav>
              |<input id="tocGrid" type="checkbox" class="visually-hidden">
              |<header>
              |${SlideEngine.render(mode, presentation.title)}
              |</header>""".trimMargin()
    }

    private fun afterMain(): String =
        SlideEngine.scripts
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
            |${afterMain().prependIndent("  ")}
            |</body>
            |</html>""".trimMargin()
        }
}


object PresentationTextRenderer : Renderer<Presentation> {

    override val mode: RenderMode = Text

    override fun render(content: Presentation): String =
        with(SlideEngine) {
            val body = content.allSlides.joinToString("\n") {
                render(mode, it)
            }

            """# ${content.sTitle}
              |
              |$body
              |""".trimMargin()
        }
}