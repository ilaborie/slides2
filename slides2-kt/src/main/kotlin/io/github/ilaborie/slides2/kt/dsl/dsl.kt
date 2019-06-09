package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.StyledText
import io.github.ilaborie.slides2.kt.engine.contents.TextContent
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle
import io.github.ilaborie.slides2.kt.jvm.asKey
import io.github.ilaborie.slides2.kt.jvm.tools.Natives


@DslMarker
annotation class PresentationMarker

internal data class LazyBuilder<T>(
    internal val id: Id,
    internal val title: Content,
    internal val builder: () -> T
)

interface PresentationDsl {
    operator fun invoke(input: Folder): Presentation
}

val String.raw: TextContent
    get() = TextContent(this, escape = false)

val String.markdown: TextContent
    get() = TextContent(Natives.markdownToHtml(this), escape = false)

val String.em: StyledText
    get() = StyledText(TextStyle.Emphasis, this.raw, emptySet())

fun pres(
    title: ContainerBuilder.() -> Unit,
    id: String,
    theme: Theme = Theme.base,
    extraStyle: String? = null,
    lang: String = "en",
    favicon:Boolean = false,
    block: PresentationBuilder.() -> Unit
): PresentationDsl = object : PresentationDsl {
    override fun invoke(input: Folder): Presentation =
        PresentationBuilder(input)
            .apply(block)
            .build(
                id = Id(id),
                title = ContainerBuilder(input).compound(title),
                theme = theme,
                extraStyle = extraStyle,
                lang = lang,
                favicon = favicon
            )
}


fun pres(
    title: String,
    id: String = title.asKey(),
    theme: Theme = Theme.base,
    extraStyle: String? = null,
    lang: String = "en",
    favicon:Boolean = false,
    block: PresentationBuilder.() -> Unit
): PresentationDsl =
    pres(
        title = { h1(title) },
        id = id,
        theme = theme,
        extraStyle = extraStyle,
        lang = lang,
        favicon = favicon,
        block = block
    )
