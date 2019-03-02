package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text

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

    val sTitle: String by lazy {
        with(SlideEngine) {
            render(Text, title)
        }
    }


}


