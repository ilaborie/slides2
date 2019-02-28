package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.base
import io.github.ilaborie.slides2.kt.jvm.asKey

/**
 * Top level Presentation
 */
data class Presentation(
    val title: Content,
    val theme: Theme = base,
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
        styles = setOf("cover", "header-hidden"),
        content = listOf(title)
    )

    val allSlides: List<Slide> by lazy {
        (listOf(coverSlide) + parts.flatMap { it.allSlides })
            .windowed(2, 1, true)
            .fold(emptyList<Slide>()) { acc, list ->
                val previous = if (acc.isEmpty()) null else acc.last().id
                val next = list.getOrNull(1)?.id
                val slide = list.first().copy(previous = previous, next = next)
                acc + slide
            }
    }

    operator fun plus(part: Part): Presentation =
        copy(parts = parts + part)
}
