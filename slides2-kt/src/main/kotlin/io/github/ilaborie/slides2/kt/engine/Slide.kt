package io.github.ilaborie.slides2.kt.engine

/**
 * A Slide
 */
data class Slide(
    val title: Content,
    val styles: Set<String> = emptySet(),
    val content: List<Content> = emptyList()
) : Content


