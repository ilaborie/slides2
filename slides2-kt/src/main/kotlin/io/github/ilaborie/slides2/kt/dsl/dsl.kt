package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.cli.Notifier
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

fun pres(
    input: Folder,
    title: String,
    theme: Theme = Theme.base,
    scripts: Set<String> = emptySet(),
    lang: String = "en",
    notifier: Notifier = Notifier.withoutTime(),
    block: PresentationBuilder.() -> Unit
): Presentation =
    SlideEngine.applyPlugins {
        PresentationBuilder(input,notifier)
            .apply(block)
            .build(title = title.h1, theme = theme, scripts = scripts, lang = lang)
    }

