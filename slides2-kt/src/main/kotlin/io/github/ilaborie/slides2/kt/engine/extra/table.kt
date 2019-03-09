package io.github.ilaborie.slides2.kt.engine.extra

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.raw
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.renderers.asHtmlClass


data class Table(val caption: Content, val data: Map<Pair<Content, Content>, Pair<Content, Set<String>>>) : Content


fun <R, C, V> ContainerBuilder.table(
    caption: Content,
    rows: List<R>,
    columns: List<C>,
    values: Map<Pair<R, C>, V>,
    rowsFn: (R) -> Content = { it.toString().raw },
    columnFn: (C) -> Content = { it.toString().raw },
    valueFn: (V) -> Content = { it.toString().raw },
    valueClassesFn: (V) -> Set<String> = { emptySet() }
) {
    val valuesFunction = { row: R, col: C -> values[row to col] }
    table(caption, rows, columns, valuesFunction, rowsFn, columnFn, valueFn, valueClassesFn)
}

fun <R, C, V> ContainerBuilder.table(
    caption: Content,
    rows: List<R>,
    columns: List<C>,
    values: (R, C) -> V?,
    rowsFn: (R) -> Content = { it.toString().raw },
    columnFn: (C) -> Content = { it.toString().raw },
    valueFn: (V) -> Content = { it.toString().raw },
    valueClassesFn: (V) -> Set<String> = { emptySet() }
) {
    content.add {
        val data = rows
            .flatMap { row -> columns.map { col -> row to col } }
            .map { (row, col) -> (row to col) to values(row, col) }
            .filter { (_, v) -> v != null }
            .toMap()
            .mapKeys { (p, _) -> rowsFn(p.first) to columnFn(p.second) }
            .mapValues { (_, v) -> valueFn(v!!) to valueClassesFn(v) }

        Table(caption, data)
    }
}


object TableTextRenderer : Renderer<Table> {
    override val mode = Html

    override fun render(content: Table): String =
        with(SlideEngine) {
            val headValues = content.data.keys
                .map { (_, v) -> v }

            val thead = "| |" + headValues.joinToString("|", postfix = "|") { render(mode, it) }

            val bodyValues = content.data.keys
                .map { (_, v) -> v }


            val tbodyRow = { k: Content ->
                "|${render(mode, k)}|${headValues.joinToString("|", postfix = "|") { v ->
                    content.data[k to v]
                        ?.let { (value, _) -> render(mode, value) }
                        ?: " "
                }}"
            }

            """$thead
            ${"-".repeat(thead.length)}
            ${bodyValues.joinToString("\n") { tbodyRow(it) }}""".trimMargin()
        }
}


object TableHtmlRenderer : Renderer<Table> {
    override val mode = Html

    override fun render(content: Table): String =
        with(SlideEngine) {
            val headValues = content.data.keys
                .map { (_, v) -> v }
                .distinct()

            val thead = "<td></td>" + headValues.joinToString("") { "<th>${render(mode, it)}</th>" }

            val bodyValues = content.data.keys
                .map { (k, _) -> k }
                .distinct()


            val tbodyRow = { k: Content ->
                """
                  |<th>${render(mode, k)}</th>
                  |${headValues.joinToString("") { v ->
                    content.data[k to v]
                        ?.let { (value, classes) ->
                            "<td${classes.asHtmlClass}>${render(mode, value)}</td>"
                        }
                        ?: "<td></td>"
                }}
                """.trimMargin()
            }

            """<table>
              |  <thead>
              |    <tr>$thead</tr>
              |  </thead>
              |  <tbody>
              |    ${bodyValues.joinToString("</tr><tr>", "<tr>", "</tr>") { tbodyRow(it) }}</tr>
              |  </tbody>
              |  <caption>${render(mode, content.caption)}</caption>
              |</table>""".trimMargin()
        }
}