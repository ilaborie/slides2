package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.jvm.asKey

/**
 * Top level Presentation
 */
data class Presentation(
    val title: Content,
    val theme: Theme = Theme.base,
    val scripts: Set<String> = emptySet(),
    val parts: List<Part> = emptyList(),
    val lang: String = "en"
) : Content {

    val sTitle: String by lazy {
        with(SlideEngine) {
            render(Text, title)
        }
    }

    val id: Id by lazy {
        Id(sTitle.asKey())
    }

    val coverSlide: Slide = Slide(
        id = Id("${id.id}_cover"),
        title = title,
        styles = setOf("header-hidden"),
        content = listOf(title)
    )

    operator fun plus(part: Part): Presentation =
        copy(parts = parts + part)
}
