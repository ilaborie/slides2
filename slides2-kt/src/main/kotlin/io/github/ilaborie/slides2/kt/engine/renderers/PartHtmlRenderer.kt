package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Part
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html


object PartHtmlRenderer : Renderer<Part> {
    override val mode: RenderMode =
        Html

    override fun render(content: Part): String =
        with(SlideEngine) {
            val header = content.headerSlide
                ?.let { listOf(content.headerSlide) }
                ?: emptyList()

            (header + content.slides).joinToString("\n") {
                render(mode, it)
            }
        }
}