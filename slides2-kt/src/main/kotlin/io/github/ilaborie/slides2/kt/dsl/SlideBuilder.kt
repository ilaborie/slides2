package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.cli.Styles
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Slide
import io.github.ilaborie.slides2.kt.engine.contents.*
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
        content.add { TextContent(html(), escape = false) }
    }

    fun sourceCode(file: String) {
        val extension = file.split(".").last()
        val language = when (extension) {
            "class.txt" -> "java"
            "dex.dump"  -> "java"
            "smali"     -> "java"
            "kt"        -> "kotlin"
            "ts"        -> "typescript"
            "js"        -> "javascript"
            "sh"        -> "bash"
            "re"        -> "reason"
            else        -> extension
        }

        if (!input.exists(file)) {
            input.writeFile(file) { "TODO fill the source file [$index] $language" }
            partDsl.presentationDsl.notifier.warning {
                "No file ${Styles.highlight(file)}, it has been created with dummy content"
            }
        }
        code(language) { input.readFileAsString(file) }
    }

    fun code(language: String, codeBlock: () -> String) {
        content.add { Code(language, codeBlock()) }
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

    fun link(href: String, block: () -> Content) {
        content.add { Link(href = href, content = block()) }
    }

    fun quote(author: String? = null, cite: String? = null, block: () -> Content) {
        content.add { Quote(author = author, cite = cite, content = block()) }
    }

}
