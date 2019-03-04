package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.engine.*
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
        part(
            partTitle = { h2(title) },
            id = id,
            skipHeader = skipHeader,
            style = style,
            block = block
        )
    }

    fun part(
        partTitle: ContainerBuilder.() -> Unit,
        id: String,
        style: String? = null,
        skipHeader: Boolean = false,
        block: PartBuilder.() -> Unit
    ) {
        val partId = Id(id)
        val title = ContainerBuilder(input).compound(partTitle)
        parts.add(LazyBuilder(partId, title) {
            PartBuilder(this)
                .apply(block)
                .build(partId, title, style, skipHeader)
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
