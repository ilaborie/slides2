package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.contents.*


object TextTextRenderer : Renderer<TextContent> {
    override val mode = Text
    override fun render(content: TextContent): String =
        content.text
}

object CompoundTextRenderer : Renderer<CompoundContent> {
    override val mode = Text
    override fun render(content: CompoundContent): String =
        with(SlideEngine) {
            content.inner
                .joinToString("\n") { render(mode, it) }
        }
}

object TitleTextRenderer : Renderer<Title> {
    override val mode = Text
    override fun render(content: Title): String =
        with(SlideEngine) {
            render(mode, content.content)
        }
}

object ParagraphTextRenderer : Renderer<Paragraph> {
    override val mode = Text
    override fun render(content: Paragraph): String =
        with(SlideEngine) {
            render(mode, content.content)
        }
}

object StyledTextTextRenderer : Renderer<StyledText> {
    override val mode = Text
    override fun render(content: StyledText): String =
        with(SlideEngine) {
            render(mode, content.content)
        }
}


object OrderedListTextRenderer : Renderer<OrderedList> {
    override val mode: RenderMode = Text

    override fun render(content: OrderedList): String =
        with(SlideEngine) {
            content.inner
                .joinToString("\n") { render(mode, it) }
        }
}

object UnorderedListTextRenderer : Renderer<UnorderedList> {
    override val mode: RenderMode = Text

    override fun render(content: UnorderedList): String =
        with(SlideEngine) {
            content.inner
                .joinToString("\n") { render(mode, it) }
        }
}

object CodeTextRenderer : Renderer<Code> {
    override val mode: RenderMode = Text

    override fun render(content: Code): String =
        """```${content.language}"
            |${content.code}
            |```""".trimMargin()
}


object LinkTextRenderer : Renderer<Link> {

    override val mode: RenderMode = Text

    override fun render(content: Link): String =
        with(SlideEngine) {
            val label = render(mode, content.content)
            if (label == content.href) "<${content.href}>"
            else """[$label](${content.href})"""
        }
}

object QuoteTextRenderer : Renderer<Quote> {

    override val mode: RenderMode = Text

    override fun render(content: Quote): String =
        with(SlideEngine) {
            val subBlock = when {
                content.author != null && content.cite != null ->
                    "> ⏤ in ${content.author} ${content.cite}"
                content.author != null                         ->
                    "> ⏤ ${content.author}"
                content.cite != null                           ->
                    "> in ${content.cite}"
                else                                           ->
                    ""
            }

            (render(mode, content.content) + "\n" + subBlock)
                .prependIndent("> ")
        }
}

object NoticeTextRenderer : Renderer<Notice> {

    override val mode: RenderMode = Text

    override fun render(content: Notice): String =
        with(SlideEngine) {
            """${content.kind.name.toLowerCase()}: ${content.title ?: ""}
                |${render(mode, content.content).prependIndent("  ")}
                |""".trimMargin()
        }
}

object FigureTextRenderer : Renderer<Figure> {
    override val mode: RenderMode = Text

    override fun render(content: Figure): String =
        content.title
}
