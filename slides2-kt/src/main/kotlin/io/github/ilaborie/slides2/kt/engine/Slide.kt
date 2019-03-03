package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.SlideEngine

/**
 * A Slide
 */
data class Slide(
    val id: Id,
    val title: Content,
    val styles: Set<String> = emptySet(),
    val content: List<Content> = emptyList(),
    val previous: Id? = null,
    val next: Id? = null
) : Content {

    override fun toString(): String =
        SlideEngine.asText(title)

}


