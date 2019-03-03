package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.*
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.contents.h2
import io.github.ilaborie.slides2.kt.jvm.asKey

@PresentationMarker
class PresentationBuilder(internal val input: Folder) {

    internal val parts: MutableList<LazyBuilder<Part>> = mutableListOf()

    fun part(
        title: String,
        id: String = title.asKey(),
        style: String? = null,
        skipHeader: Boolean = false,
        block: PartBuilder.() -> Unit
    ) {
        part(partTitle = title.h2, id = id, skipHeader = skipHeader, style = style, block = block)
    }

    fun part(
        partTitle: Content,
        id: String = with(SlideEngine) { render(Text, partTitle) },
        style: String? = null,
        skipHeader: Boolean = false,
        block: PartBuilder.() -> Unit
    ) {
        val partId = Id(id)
        parts.add(LazyBuilder(partId, partTitle) {
            PartBuilder(this)
                .apply(block)
                .build(partId, partTitle, style, skipHeader)
        })
    }

    fun build(id: Id, title: Content, theme: Theme, extraStyle: String?, lang: String): Presentation =
        Presentation(
            id = id,
            title = title,
            theme = theme,
            extraStyle = extraStyle,
            parts = parts.map { it.builder() },
            lang = lang
        )

}
