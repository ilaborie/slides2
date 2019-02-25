package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.engine.*
import io.github.ilaborie.slides2.kt.engine.contents.h2
import io.github.ilaborie.slides2.kt.jvm.asKey

@PresentationMarker
class PresentationBuilder(internal val input: Folder) {

    internal val parts: MutableList<LazyBuilder<Part>> = mutableListOf()

    fun part(
        title: String,
        style: String? = null,
        block: PartBuilder.() -> Unit
    ) {
        val partTitle = title.h2
        val partIndex = parts.size + 1
        val partId = Id("${partIndex}_${title.asKey()}")
        parts.add(LazyBuilder(partId, partTitle) {
            PartBuilder(partIndex, this)
                .apply(block)
                .build(partId, partTitle, style)
        })
    }

    fun build(
        title: Content,
        theme: Theme,
        scripts: Set<String>,
        lang: String
    ): Presentation =
        Presentation(
            title = title,
            theme = theme,
            scripts = scripts,
            parts = parts.map { it.builder() },
            lang = lang
        )

}
