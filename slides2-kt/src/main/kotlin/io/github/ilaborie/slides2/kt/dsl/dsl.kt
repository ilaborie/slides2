package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.*
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.contents.h1
import io.github.ilaborie.slides2.kt.jvm.asKey


@DslMarker
annotation class PresentationMarker

internal data class Info(internal val title: Content) {
    internal val id: String by lazy {
        with(SlideEngine) {
            render(Text, title)
                .asKey()
        }
    }
}

internal typealias LazySlide = Pair<Info, () -> Slide>
internal typealias LazyPart = Pair<Info, () -> Part>
internal typealias LazyContent = () -> Content

fun pres(
    title: String,
    theme: Theme = Theme.base,
    scripts: Set<String> = emptySet(),
    lang: String = "en",
    block: PresentationBuilder.() -> Unit
): Presentation =
//    SlideEngine.applyPlugins {
        PresentationBuilder()
            .apply(block)
            .build(title = title.h1, theme = theme, scripts = scripts, lang = lang)
//    }

