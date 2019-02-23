package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html


object PresentationHtmlRenderer : Renderer<Presentation> {
    override val mode: RenderMode =
        Html

    override fun render(content: Presentation): String =
        with(SlideEngine) {
            // FIXME Themes
            val body = content.content.joinToString("\n") { render(mode, it) }
            """<!doctype html>
            |<html lang="${content.lang}">
            |<head>
            |  <meta charset="utf-8">
            |  <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">
            |  <title>${content.sTitle}</title>
            |</head>
            |<body>
            |  <header>
            ${render(mode, content.title).prependIndent("    ")}
            |  </header>
            |  <main>
            |    <section id="${content.key}" class="cover">
            |${render(mode, content.title).prependIndent("      ")}
            |    </section>
            |${body.prependIndent("    ")}
            |  </main>
            |</body>
            |</html>
        """.trimMargin()
        }
}