package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.contents.*
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.*
import io.github.ilaborie.slides2.kt.jvm.esccapeHtml


abstract class HtmlTagRenderer<T : Content> : Renderer<T> {
    override val mode = Html
    abstract fun tag(content: T): String
    open fun classes(content: T): List<String> = emptyList()
    open fun innerContent(content: T): Content? = null
    override fun render(content: T): String =
        with(SlideEngine) {
            val t = tag(content)
            val cls = classes(content)
            val tagClass = if (cls.isEmpty()) "" else "class=\"${cls.joinToString(" ")}\""
            innerContent(content)
                ?.let { inner -> "<$t$tagClass>${render(mode, inner)}</$t>" }
                ?: "<$t$tagClass/>"

        }
}

object TextHtmlRenderer : Renderer<TextContent> {
    override val mode = Html
    override fun render(content: TextContent): String =
        if (content.escape) content.text.esccapeHtml() else content.text
}

object TitleHtmlRenderer : HtmlTagRenderer<Title>() {
    override fun tag(content: Title): String =
        "h${content.level}"

    override fun innerContent(content: Title): Content? =
        content.content
}

object ParagraphHtmlRenderer : HtmlTagRenderer<Paragraph>() {
    override fun tag(content: Paragraph): String =
        "p"

    override fun innerContent(content: Paragraph): Content? =
        content.content
}

object StyledTextHtmlRenderer : HtmlTagRenderer<StyledText>() {
    override fun tag(content: StyledText): String =
        when (content.style) {
            Strong    -> "strong"
            Emphasis  -> "em"
            UnderLine -> "u"
            Mark      -> "mark"
            Keyboard  -> "kbd"
            Pre       -> "pre"
            Ins       -> "ins"
            Del       -> "del"
            Sub       -> "sub"
            Sup       -> "sup"
        }

    override fun innerContent(content: StyledText): Content? =
        content.content
}


object OrderedListHtmlRenderer : Renderer<OrderedList> {
    override val mode: RenderMode = Html

    override fun render(content: OrderedList): String {
        val body = with(SlideEngine) {
            content.inner
                .joinToString("\n") {
                    """<li>
                            |${render(mode, it).prependIndent("  ")}
                            |</li>""".trimMargin()
                }
        }
        return """<ol>
                |${body.prependIndent("  ")}
                |</ol>""".trimMargin()
    }
}

object UnorderedListHtmlRenderer : Renderer<UnorderedList> {
    override val mode: RenderMode = Html

    override fun render(content: UnorderedList): String {
        val body = with(SlideEngine) {
            content.inner
                .joinToString("\n") {
                    """<li>
                            |${render(mode, it).prependIndent("  ")}
                            |</li>""".trimMargin()
                }
        }
        return """<ul>
                |${body.prependIndent("  ")}
                |</ul>""".trimMargin()
    }
}