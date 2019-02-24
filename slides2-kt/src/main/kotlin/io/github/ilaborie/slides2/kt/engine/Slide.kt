package io.github.ilaborie.slides2.kt.engine

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

    operator fun plus(c: Content): Slide =
        copy(content = content + c)

}


