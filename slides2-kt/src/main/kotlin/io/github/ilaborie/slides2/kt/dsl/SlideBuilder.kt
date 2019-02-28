package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.cli.Styles
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Slide
import io.github.ilaborie.slides2.kt.engine.contents.Text
import io.github.ilaborie.slides2.kt.engine.contents.ol
import io.github.ilaborie.slides2.kt.engine.contents.p
import io.github.ilaborie.slides2.kt.engine.contents.ul
import io.github.ilaborie.slides2.kt.jvm.tools.MarkdownToHtml.markdownToHtml

@PresentationMarker
class SlideBuilder(internal val index: Int, private val partDsl: PartBuilder) {

    private val input = partDsl.presentationDsl.input

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
        if (!input.exists(file)) {
            input.writeFile(file) { "TODO fill the slide [$index]" }
            partDsl.presentationDsl.notifier.warning {
                "No file ${Styles.highlight(file)}, it has been created with dummy content"
            }
        }
        val content = { input.readFileAsString(file) }
        when (extension) {
            "md" -> markdown(content)
            "html" -> html(content)
            "svg" -> html(content)
            else ->
                throw IllegalArgumentException("Unexpected file type, only *.{md,html} are supported yet, got $file")
        }
    }

    fun markdown(md: () -> String) {
        html { markdownToHtml(md()) }
    }

    fun html(html: () -> String) {
        content.add { Text(html(), escape = false) }
    }

    fun p(text: () -> String) {
        content.add { text().p }
    }

    fun ol(list: () -> List<Content>) {
        content.add { list().ol }
    }

    fun ul(list: () -> List<Content>) {
        content.add { list().ul }
    }

}
