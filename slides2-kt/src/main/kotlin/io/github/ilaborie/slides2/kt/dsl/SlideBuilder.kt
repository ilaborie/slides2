package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Slide
import io.github.ilaborie.slides2.kt.engine.contents.Text
import io.github.ilaborie.slides2.kt.engine.contents.ol
import io.github.ilaborie.slides2.kt.engine.contents.p
import io.github.ilaborie.slides2.kt.engine.contents.ul
import io.github.ilaborie.slides2.kt.jvm.tools.MarkdownToHtml

@PresentationMarker
class SlideBuilder(internal val index: Int, internal val partDsl: PartBuilder) {

    private val content: MutableList<() -> Content> = mutableListOf()

    internal fun build(id: Id, title: Content, styles: Set<String>): Slide =
        Slide(
            id = id,
            title = title,
            styles = styles,
            content = content.map { it() }
        )

    fun file(file: String) {
        val extension = file.split(".").last()
        val content = partDsl.presentationDsl.input.readFileAsString(file)
        when (extension) {
            "md" -> markdown(content)
            "html" -> html(content)
            else ->
                throw IllegalArgumentException("Unexpected file type, only *.{md,html} are supported yet, got $file")
        }
    }

    fun markdown(md: String) {
        val html = MarkdownToHtml.markdownToHtml(md)
        html(html)
    }

    fun html(html: String) {
        content.add { Text(html, escape = false) }
    }

    fun p(text: String) {
        content.add { text.p }
    }

    fun ol(list: () -> List<Content>) {
        content.add { list().ol }
    }

    fun ul(list: () -> List<Content>) {
        content.add { list().ul }
    }

}
