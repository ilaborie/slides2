package io.github.ilaborie.slides2.kt.engine

/**
 * A Part
 */
data class Part(
    val title: Content,
    val style: String? = null,
    val headerSlide: Slide? = generateDefaultSlide(title),
    val slides: List<Slide> = emptyList()
) : Content {

    operator fun plus(slide: Slide): Part =
        copy(slides = slides + slide)

    companion object {
        const val style = "part"

        fun generateDefaultSlide(title: Content): Slide =
            Slide(title = title, styles = setOf(style))
    }
}