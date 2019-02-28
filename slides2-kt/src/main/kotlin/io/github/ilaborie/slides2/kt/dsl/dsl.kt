package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.h1


@DslMarker
annotation class PresentationMarker

internal data class LazyBuilder<T>(
    internal val id: Id,
    internal val title: Content,
    internal val builder: () -> T
)

typealias ConfigToPresentation = (Config) -> Presentation

fun pres(
    title: String,
    theme: Theme = Theme.base,
    lang: String = "en",
    block: PresentationBuilder.() -> Unit
): ConfigToPresentation = { config ->
    SlideEngine.applyPlugins {
        PresentationBuilder(config.input, config.notifier)
            .apply(block)
            .build(title = title.h1, theme = theme, lang = lang)
    }
}

