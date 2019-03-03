package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.SlideEngine.asText
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.base
import io.github.ilaborie.slides2.kt.jvm.asKey

/**
 * Top level Presentation
 */
data class Presentation(
    val title: Content,
    val sTitle: String = asText(title),
    val id: Id = Id(sTitle.asKey()),
    val theme: Theme = base,
    val extraStyle: String? = null,
    val parts: List<Part> = emptyList(),
    val lang: String = "en"
) : Content {

    private val coverSlide: Slide = Slide(
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

    override fun toString(): String =
        sTitle
}
