package io.github.ilaborie.slides2.kt.engine.extra

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html


data class Table(val caption: Content, val data: Map<Pair<Content, Content>, Content>) : Content


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
                        ?.let { render(mode, it) }
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
                .map { (_, v) -> v }
                .distinct()


            val tbodyRow = { k: Content ->
                """
                  |<th>${render(mode, k)}</th>
                  |${headValues.joinToString("") { v ->
                    content.data[k to v]
                        ?.let { "<td>${render(mode, it)}</td>" }
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