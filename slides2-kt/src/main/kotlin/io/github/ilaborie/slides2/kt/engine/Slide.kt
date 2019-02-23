package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.jvm.asKey

/**
 * A Slide
 */
data class Slide(
    val title: Content,
    val styles: Set<String> = emptySet(),
    val content: List<Content> = emptyList()
) : Content {

    val sTitle: String by lazy {
        with(SlideEngine) {
            render(Renderer.Companion.RenderMode.Text, title)
        }
    }

    val key: String by lazy {
        sTitle.asKey()
    }

    operator fun plus(c: Content): Slide =
        copy(content = content + c)

}


