package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.contents.*
import io.github.ilaborie.slides2.kt.engine.extra.Speaker
import io.github.ilaborie.slides2.kt.engine.extra.Table
import io.github.ilaborie.slides2.kt.jvm.tools.MarkdownToHtml.markdownToHtml
import io.github.ilaborie.slides2.kt.term.Notifier
import io.github.ilaborie.slides2.kt.term.Styles

@PresentationMarker
open class ContainerBuilder(private val input: Folder) {

    internal val content: MutableList<() -> Content> = mutableListOf()

    fun build(): List<Content> =
        content.map { it() }

    fun compound(block: ContainerBuilder.() -> Unit): Content {
        val builder = ContainerBuilder(input)
        builder.apply(block)
        val lst = builder.content.map { it() }
        return CompoundContent(lst)
    }

    fun file(file: String) {
        val extension = file.split(".").last()
        if (!input.exists(file)) {
            input.writeFile(file) { "TODO fill" }
            Notifier.warning {
                "No file ${Styles.highlight(file)}, it has been created with dummy content"
            }
        }
        val content = { input.readFileAsString(file) }
        when (extension) {
            "md"   -> markdown(content)
            "html" -> html(content)
            "svg"  -> html(content)
            else   ->
                throw IllegalArgumentException("Unexpected file type, only *.{md,html} are supported yet, got $file")
        }
    }

    fun markdown(md: () -> String) {
        html { markdownToHtml(md()) }
    }


    fun todo(html: () -> String) {
        html {
            """<span class="todo">${html()}</span>"""
        }
    }


    fun html(html: () -> String) {
        content.add { TextContent(html(), escape = false) }
    }

    fun h1(title: String, classes: Set<String> = emptySet()) {
        content.add { Title(1, title.raw, classes) }
    }

    fun h1(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(1, classes, block)
    }

    fun h2(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(2, title.raw, classes)
        }
    }

    fun h2(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(2, classes, block)
    }

    fun h3(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(2, title.raw, classes)
        }
    }

    fun h3(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(3, classes, block)
    }

    fun h4(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(4, title.raw, classes)
        }
    }

    fun h4(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(4, classes, block)
    }

    fun h5(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(5, title.raw, classes)
        }
    }

    fun h5(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(5, classes, block)
    }

    fun h6(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(6, title.raw, classes)
        }
    }

    fun h6(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(6, classes, block)
    }

    fun styledText(style: TextStyle, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            StyledText(style, c, classes)
        }
    }


    fun strong(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.Strong, classes, block)
    }

    fun em(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.Emphasis, classes, block)
    }

    fun u(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.UnderLine, classes, block)
    }

    fun mark(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.Mark, classes, block)
    }

    fun kbd(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.Keyboard, classes, block)
    }

    fun pre(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.Pre, classes, block)
    }

    fun ins(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.Ins, classes, block)
    }

    fun del(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.Del, classes, block)
    }

    fun sub(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.Sub, classes, block)
    }

    fun sup(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(TextStyle.Sup, classes, block)
    }

    fun span(inner: String) {
        html { """<span>$inner</span>""" }
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
            input.writeFile(file) { "TODO fill the source file $language" }
            Notifier.warning {
                "No file ${Styles.highlight(file)}, it has been created with dummy content"
            }
        }
        code(language) { input.readFileAsString(file) }
    }

    fun code(language: String, codeBlock: () -> String) {
        content.add { Code(language, codeBlock()) }
    }

    fun p(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            Paragraph(ContainerBuilder(input).compound(block), classes)
        }
    }

    fun p(text: String, classes: Set<String> = emptySet()) {
        content.add { Paragraph(text.raw, classes) }
    }

    fun ol(steps: Boolean = false, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            OrderedList(
                steps = steps,
                classes = classes,
                inner = ContainerBuilder(input).apply(block).build()
            )
        }
    }

    fun ul(steps: Boolean = false, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            UnorderedList(
                steps = steps,
                classes = classes,
                inner = ContainerBuilder(input).apply(block).build()
            )
        }
    }

    fun link(href: String, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit = { href.raw }) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            Link(href = href, content = c, classes = classes)
        }
    }

    fun link(href: String, content: String) {
        link(href) { content.raw }
    }

    fun quote(
        author: String? = null,
        cite: String? = null,
        classes: Set<String> = emptySet(),
        block: ContainerBuilder.() -> Unit
    ) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            Quote(author = author, cite = cite, classes = classes, content = c)
        }
    }

    fun notice(
        kind: NoticeKind,
        title: String?,
        classes: Set<String> = emptySet(),
        block: ContainerBuilder.() -> Unit
    ) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            Notice(kind = kind, title = title, classes = classes, content = c)
        }
    }

    fun figure(src: String, title: String, copyrightBlock: Content? = null) {
        content.add {
            val figSrc = input.readFileAsDataUri(src)
            Figure(src = figSrc, title = title, copyright = copyrightBlock)
        }
    }

    private fun title(level: Int, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            Title(level = level, classes = classes, content = c)
        }
    }

    fun title(level: Int, text: String) {
        title(level) { text.raw }
    }


    fun table(
        caption: String,
        rows: List<String>,
        columns: List<String>,
        values: Map<Pair<String, String>, String>,
        rowsFn: (String) -> Content = { it.raw },
        columnFn: (String) -> Content = { it.raw },
        valueFn: (String) -> Content = { it.raw }
    ) {
        val valuesFunction = { row: String, col: String -> values[row to col] }
        table(caption, rows, columns, valuesFunction, rowsFn, columnFn, valueFn)
    }

    fun table(
        caption: String,
        rows: List<String>,
        columns: List<String>,
        values: (String, String) -> String?,
        rowsFn: (String) -> Content = { it.raw },
        columnFn: (String) -> Content = { it.raw },
        valueFn: (String) -> Content = { it.raw }
    ) {
        content.add {
            val data = rows
                .flatMap { row -> columns.map { col -> row to col } }
                .map { (row, col) -> (row to col) to values(row, col) }
                .filter { (_, v) -> v != null }
                .toMap()
                .mapKeys { (p, _) -> rowsFn(p.first) to columnFn(p.second) }
                .mapValues { (_, v) -> valueFn(v!!) }

            Table(caption.raw, data)
        }
    }

    fun barChart(title: String, data: Map<String, Double>, unit: String) {
        // FIXME Table
        html {
            val rows = data.map { (title, value) ->
                """<tr>
                |  <th scope="row">$title</th>
                |  <td style="--value: $value">
                |    <span>$value $unit</span>
                |  </td>
                |</tr>"""
            }.joinToString("\n")

            """<table class="table-charts bar" style="--scale: ${data.values.max()}">
               |  <caption>$title</caption>
               |  <tbody>$rows</tbody>
               |</table>""".trimMargin()
        }
    }

    fun speaker(fullName: String, src: String, info: String, links: Map<String, String>, classes: Set<String>) {
        content.add {
            val img = input.readFileAsDataUri(src)
            Speaker(
                fullName = fullName,
                info = info,
                img = img,
                links = links,
                classes = classes
            )
        }
    }

}
