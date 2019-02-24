package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.contents.*
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Emphasis
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Keyboard
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Mark
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Pre
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Strong
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.UnderLine


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

object TextHtmlRenderer : Renderer<Text> {
    override val mode = Html
    override fun render(content: Text): String =
        content.text // FIXME escape
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