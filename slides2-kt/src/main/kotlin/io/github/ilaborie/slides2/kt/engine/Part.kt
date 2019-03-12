package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.SlideEngine

/**
 * A Part
 */
data class Part(
    val id: Id,
    val title: Content,
    val skipHeader: Boolean = false,
    val style: String? = null,
    val slides: List<Slide> = emptyList()
) : Content {

    private val headerSlide: Slide by lazy {
        Slide(
            id = Id("${id.id}_part"),
            title = title,
            classes = setOf("header-hidden", "part"),
            content = listOf(title)
        )
    }

    val allSlides: List<Slide> by lazy {
        if (skipHeader) slides
        else listOf(headerSlide) + slides
    }

    override fun toString(): String =
            SlideEngine.asText(title)
}