package io.github.ilaborie.slides2.kt.engine.extra

import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.jvm.asKey

data class BarChart(
    val title: String,
    val values: Map<String, Double>,
    val unit: String,
    val factor: (Double) -> Int = { it.toInt() }
) : Content {
    val max: Int by lazy {
        values.values
            .map(factor)
            .max() ?: throw AssertionError("WTF")
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

        val rows = content.values.map { (label, value) ->
            val id = "${content.title}_$label".asKey()
            val info = "$value ${content.unit}"
            """<label for="$id">$label</label>
              |<meter id="$id" value="${content.factor(value)}" max="${content.max}" title="$info">$info</meter>
              |<div class="info">$info</div>"""
        }.joinToString("\n")

        return """<div class="charts bar">
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