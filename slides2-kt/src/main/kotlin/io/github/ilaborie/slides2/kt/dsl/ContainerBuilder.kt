package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.contents.*
import io.github.ilaborie.slides2.kt.engine.extra.Speaker
import io.github.ilaborie.slides2.kt.jvm.tools.MarkdownToHtml.markdownToHtml
import io.github.ilaborie.slides2.kt.term.Notifier
import io.github.ilaborie.slides2.kt.term.Styles

@PresentationMarker
open class ContainerBuilder(private val input: Folder) {

    internal val content: MutableList<() -> Content> = mutableListOf()

    fun build(): List<Content> =
        content.map { it() }

    fun single(block: ContainerBuilder.() -> Unit): Content =
        ContainerBuilder(input)
            .apply(block)
            .content.asSequence()
            .map { it() }
            .firstOrNull() ?: throw IllegalStateException("No content")

    fun compound(block: ContainerBuilder.() -> Unit): Content =
        ContainerBuilder(input)
            .apply(block)
            .content
            .map { it() }
            .let { CompoundContent(it) }

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

    fun h1(classes: Set<String> = emptySet(), title: String) {
        title(1, classes) { title.raw }
    }

    fun h1(classes: Set<String> = emptySet(), block: () -> Content) {
        title(1, classes, block)
    }

    fun h2(classes: Set<String> = emptySet(), title: String) {
        title(2, classes) { title.raw }
    }

    fun h2(classes: Set<String> = emptySet(), block: () -> Content) {
        title(2, classes, block)
    }

    fun h3(classes: Set<String> = emptySet(), title: String) {
        title(3, classes) { title.raw }
    }

    fun h3(classes: Set<String> = emptySet(), block: () -> Content) {
        title(3, classes, block)
    }

    fun h4(classes: Set<String> = emptySet(), title: String) {
        title(4, classes) { title.raw }
    }

    fun h4(classes: Set<String> = emptySet(), block: () -> Content) {
        title(4, classes, block)
    }

    fun h5(classes: Set<String> = emptySet(), title: String) {
        title(5, classes) { title.raw }
    }

    fun h5(classes: Set<String> = emptySet(), block: () -> Content) {
        title(5, classes, block)
    }

    fun h6(classes: Set<String> = emptySet(), title: String) {
        title(6, classes) { title.raw }
    }

    fun h6(classes: Set<String> = emptySet(), block: () -> Content) {
        title(6, classes, block)
    }


    fun strong(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.Strong, block(), classes) }
    }

    fun em(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.Emphasis, block(), classes) }
    }

    fun u(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.UnderLine, block(), classes) }
    }

    fun mark(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.Mark, block(), classes) }
    }

    fun kbd(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.Keyboard, block(), classes) }
    }

    fun pre(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.Pre, block(), classes) }
    }

    fun ins(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.Ins, block(), classes) }
    }

    fun del(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.Del, block(), classes) }
    }

    fun sub(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.Sub, block(), classes) }
    }

    fun sup(classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { StyledText(TextStyle.Sup, block(), classes) }
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

    fun p(text: () -> String) {
        content.add { text().p }
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

    fun link(href: String, classes: Set<String> = emptySet(), block: () -> Content = { href.raw }) {
        content.add { Link(href = href, content = block(), classes = classes) }
    }

    fun link(href: String, content: String) {
        link(href) { content.raw }
    }

    fun quote(author: String? = null, cite: String? = null, classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { Quote(author = author, cite = cite, classes = classes, content = block()) }
    }

    fun notice(kind: NoticeKind, title: String?, classes: Set<String> = emptySet(), block: () -> Content) {
        content.add { Notice(kind = kind, title = title, classes = classes, content = block()) }
    }

    fun figure(src: String, title: String, copyrightBlock: Content? = null) {
        content.add {
            val figSrc = input.readFileAsDataUri(src)
            Figure(src = figSrc, title = title, copyright = copyrightBlock)
        }
    }

    private fun title(level: Int, classes: Set<String> = emptySet(), block: () -> Content) {
        content.add {
            Title(level = level, classes = classes, content = block())
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
