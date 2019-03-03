package io.github.ilaborie.slides2.kt.engine.extra

import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text

data class BarChart(
    val title: String,
    val values: Map<String, Double>,
    val unit: String,
    val factor: (Double) -> Int = { it.toInt() }
) : Content {
    val max: Double by lazy {
        values.values.max() ?: throw AssertionError("WTF")
    }

    init {
        require(values.isNotEmpty()) { "Require at least one data" }
    }
}


fun ContainerBuilder.barChart(
    title: String,
    values: Map<String, Double>,
    unit: String,
    factor: (Double) -> Int = { it.toInt() }
) {
    content.add { BarChart(title, values, unit, factor) }

}


object BarChartHtmlRenderer : Renderer<BarChart> {

    override val mode: RenderMode = Html

    override fun render(content: BarChart): String {

        val rows = content.values.map { (title, value) ->
            """<div class="label">$title</div>
              |<div class="value" style="--value: ${content.factor(value)}"></div>
              |<div class="info">$value ${content.unit}</div>"""
        }.joinToString("\n")

        return """<div class="charts bar" style="--scale: ${content.factor(content.max)}">
                 |  <div class="body">$rows</div>
                 |  <div class="caption">${content.title}</div>
                 |</div>""".trimMargin()
    }
}

object BarChartTextRenderer : Renderer<BarChart> {

    override val mode: RenderMode = Text

    override fun render(content: BarChart): String =
        content.title
}