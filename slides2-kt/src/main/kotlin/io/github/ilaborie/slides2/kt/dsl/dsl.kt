package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.h1
import io.github.ilaborie.slides2.kt.jvm.asKey


@DslMarker
annotation class PresentationMarker

internal data class LazyBuilder<T>(
    internal val id: Id,
    internal val title: Content,
    internal val builder: () -> T
)

typealias ConfigToPresentation = (Folder) -> Presentation

fun pres(
    title: String,
    id: String = title.asKey(),
    theme: Theme = Theme.base,
    extraStyle: String? = null,
    lang: String = "en",
    block: PresentationBuilder.() -> Unit
): ConfigToPresentation = { input ->
    SlideEngine.applyPlugins {
        PresentationBuilder(input)
            .apply(block)
            .build(id = Id(id), title = title.h1, theme = theme, extraStyle = extraStyle, lang = lang)
    }
}

