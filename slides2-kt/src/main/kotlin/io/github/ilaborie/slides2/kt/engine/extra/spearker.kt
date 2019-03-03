package io.github.ilaborie.slides2.kt.engine.extra

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text


data class Speaker(
    val fullName: String,
    val img: String,
    val info: String,
    val links: Map<String, String> = emptyMap(),
    val classes: Set<String> = emptySet()
) : Content


object SpeakerHtmlRenderer : Renderer<Speaker> {

    override val mode: RenderMode = Html

    override fun render(content: Speaker): String =
        """<div class="speaker ${content.classes.joinToString(",")}">
                |  <img src="${content.img}" alt="${content.fullName}">
                |  <h4>${content.fullName}</h4>
                |  <p>${content.info}</p>
                |  ${content.links.map { (label, url) -> """<a href="$url">$label</a>""" }.joinToString("\n")}
                |</div>""".trimMargin()
}

object SpeakerTextRenderer : Renderer<Speaker> {

    override val mode: RenderMode = Text

    override fun render(content: Speaker): String =
        """${content.fullName}
          |  ${content.info}""".trimMargin()
}