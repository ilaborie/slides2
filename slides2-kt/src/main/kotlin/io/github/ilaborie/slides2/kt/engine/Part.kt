package io.github.ilaborie.slides2.kt.engine
/**
 * A Part
 */
data class Part(
    val title: Content,
    val style: String? = null,
    val headerSlide: Slide? = generateDefaultSlide(),
    val slides: List<Slide> = emptyList()
) : Content {
    companion object {
        fun generateDefaultSlide(): Slide =
            TODO()
    }
}