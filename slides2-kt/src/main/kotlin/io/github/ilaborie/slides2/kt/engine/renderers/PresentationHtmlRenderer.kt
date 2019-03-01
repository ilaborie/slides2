package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html

data class Script(
    val src: String,
    val defer: Boolean = true,
    val module: Boolean = true,
    val async: Boolean = false
) {
    private val tags: String =
        ((if (defer) listOf("defer") else emptyList()) +
                (if (async) listOf("async") else emptyList()))
            .joinToString(separator = " ") +
                (if (module) " type=\"module\"" else "")

    fun asHtml(): String =
        """<script $tags src="$src"></script>"""
}

// FIXME add global progress
open class PresentationHtmlRenderer(
    open val scripts: List<Script> = emptyList(),
    open val stylesheets: List<String> = emptyList()
) : Renderer<Presentation> {
    override val mode: RenderMode = Html

    open fun head(presentation: Presentation): String {
        val scripts = (SlideEngine.globalScripts.map { Script("./$it") } + scripts)
            .filter { it.module }
            .joinToString("\n") { it.asHtml() }

        val stylesheets = (listOf("./${presentation.theme.name}.css") + stylesheets)
            .joinToString("\n") {
                """<link rel="stylesheet" href="$it" media="all">"""
            }
        return """<head>
                |  <meta charset="utf-8">
                |  <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">
                |${stylesheets.prependIndent("  ")}
                |${scripts.prependIndent("  ")}
                |  <title>${presentation.sTitle}</title>
                |</head>""".trimMargin()
    }

    open fun beforeMain(presentation: Presentation): String =
        """<header class="no-print">
            |  ${SlideEngine.render(mode, presentation.title)}
            |  <a href="#${presentation.coverSlide.id.id}">📺</a>
            |</header>""".trimMargin()

    open fun afterMain(presentation: Presentation): String =
        scripts
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