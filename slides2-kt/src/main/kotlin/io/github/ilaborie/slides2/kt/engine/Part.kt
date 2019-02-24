package io.github.ilaborie.slides2.kt.engine

/**
 * A Part
 */
data class Part(
    val id: Id,
    val title: Content,
    val style: String? = null,
    val slides: List<Slide> = emptyList()
) : Content {

    val headerSlide: Slide = Slide(
        id = Id("${id.id}_part"),
        title = title,
        styles = setOf("header-hidden", "part")
    )

    val allSlides: List<Slide> by lazy {
        listOf(headerSlide) + slides
    }

    operator fun plus(slide: Slide): Part =
        copy(slides = slides + slide)
}