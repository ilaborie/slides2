package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.jvm.asKey

/**
 * Top level Presentation
 */
data class Presentation(
    val title: Content,
    val theme: Theme = Theme.base,
    val style: String? = null,
    val scripts: Set<String> = emptySet(),
    val content: List<Content> = emptyList(),
    val lang: String = "en"
) : Content {

    val sTitle: String by lazy {
        with(SlideEngine) {
            render(Text, title)
        }
    }

    val key: String by lazy {
        sTitle.asKey()
    }

    operator fun plus(part: Part): Presentation =
        copy(content = content + part)
}
