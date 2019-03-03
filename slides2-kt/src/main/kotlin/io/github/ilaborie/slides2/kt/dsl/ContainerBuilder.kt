package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.contents.*
import io.github.ilaborie.slides2.kt.jvm.tools.MarkdownToHtml.markdownToHtml
import io.github.ilaborie.slides2.kt.term.Notifier
import io.github.ilaborie.slides2.kt.term.Styles

@PresentationMarker
open class ContainerBuilder(private val input: Folder) {

    private val content: MutableList<() -> Content> = mutableListOf()

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

    fun ol(steps: Boolean = false, block: ContainerBuilder.() -> Unit) {
        content.add {
            ContainerBuilder(input)
                .apply(block)
                .build()
                .ol
                .copy(steps = steps)
        }
    }

    fun ul(steps: Boolean = false, block: ContainerBuilder.() -> Unit) {
        content.add {
            ContainerBuilder(input)
                .apply(block)
                .build()
                .ul
                .copy(steps = steps)
        }
    }

    fun link(href: String, block: () -> Content = { href.raw }) {
        content.add { Link(href = href, content = block()) }
    }

    fun link(href: String, content: String) {
        link(href) { content.raw }
    }

    fun quote(author: String? = null, cite: String? = null, block: () -> Content) {
        content.add { Quote(author = author, cite = cite, content = block()) }
    }

    fun notice(kind: NoticeKind, title: String?, block: () -> Content) {
        content.add { Notice(kind = kind, title = title, content = block()) }
    }

    fun figure(src: String, title: String, copyrightBlock: Content? = null) {
        content.add {
            val figSrc = if (input.exists(src)) {
                val extension = src.split(".").last()
                val mimeType = when (extension) {
                    "svg" -> "image/svg+xml;base64"
                    "png" -> "image/png;base64"
                    "gif" -> "image/gif;base64"
                    "jpg" -> "image/jpeg;base64"
                    else  -> throw IllegalArgumentException("Unsupported file extension: $extension")
                }
                "data:$mimeType,${input.readFileAsBase64(src)}"
            } else src
            Figure(src = figSrc, title = title, copyright = copyrightBlock)
        }
    }

    fun title(level: Int, block: () -> Content) {
        content.add {
            Title(level = level, content = block())
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

}
