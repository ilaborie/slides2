package io.github.ilaborie.slides2.kt.engine

/**
 * Top level Presentation
 */
data class Presentation(
    val title: Content,
    val theme: Theme = Theme.base,
    val style: String? = null,
    val scripts: Set<String> = emptySet(),
    val content: List<Content> = emptyList()
) : Content {
    val key: String by lazy {

        //renderAsString + normalize
        TODO()
    }
}
