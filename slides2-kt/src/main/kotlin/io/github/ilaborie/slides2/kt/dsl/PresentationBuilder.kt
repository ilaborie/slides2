package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.h2

@PresentationMarker
class PresentationBuilder {

    internal val parts: MutableList<LazyPart> = mutableListOf()

    fun part(
        title: String,
        style: String? = null,
        block: PartBuilder.() -> Unit
    ) {
        val partTitle = title.h2
        parts.add(Info(partTitle) to {
            PartBuilder(this)
                .apply(block)
                .build(partTitle, style)
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
            content = parts.map { it.second() },
            lang = lang
        )

}
