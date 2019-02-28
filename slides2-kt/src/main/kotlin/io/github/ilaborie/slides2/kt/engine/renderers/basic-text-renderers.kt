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
