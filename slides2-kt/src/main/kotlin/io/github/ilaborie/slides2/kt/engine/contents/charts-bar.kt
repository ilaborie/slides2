package io.github.ilaborie.slides2.kt.engine.contents

import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.contents.BarChart.Companion.BarChartMode
import io.github.ilaborie.slides2.kt.engine.contents.BarChart.Companion.BarChartSmallerBetter
import io.github.ilaborie.slides2.kt.jvm.asKey




data class BarChart<T>(
    val title: String,
    val values: Map<String, T>,
    val unit: String,
    val infoFn: (T) -> String,
    val factor: (T) -> Int,
    val mode: BarChartMode
) : Content {

    init {
        require(values.isNotEmpty()) { "Require at least one data" }
    }

    companion object {
        interface BarChartMode {
            fun min(values: List<Int>): Number
            fun low(values: List<Int>): Number
            fun high(values: List<Int>): Number
            fun max(values: List<Int>): Number
            fun optimum(values: List<Int>): Number
        }

        class BarChartSmallerBetter(private val fixedMin: Int? = null, private val fixedMax: Int? = null) : BarChartMode {
            override fun min(values: List<Int>): Number =
                fixedMin ?: values.min() ?: 0

            override fun low(values: List<Int>): Number =
                values.min()?.let { it * 1.15 } ?: min(values)


            override fun high(values: List<Int>): Number =
                values.min()?.let { it * 1.5 } ?: min(values)

            override fun max(values: List<Int>): Number =
                fixedMax ?: values.max() ?: 100

            override fun optimum(values: List<Int>): Number =
                min(values)

        }

        class BarChartHigherBetter(private val fixedMin: Int? = null, private val fixedMax: Int? = null) : BarChartMode {
            override fun min(values: List<Int>): Number =
                fixedMin ?: values.min() ?: 0

            override fun low(values: List<Int>): Number =
                values.max()?.let { it * 0.9 } ?: min(values)


            override fun high(values: List<Int>): Number =
                values.max()?.let { it * 0.667 } ?: min(values)

            override fun max(values: List<Int>): Number =
                fixedMax ?: values.max() ?: 100

            override fun optimum(values: List<Int>): Number =
                max(values)

        }

        class BarChartCustom(
            private val min: Number,
            private val low: Number,
            private val high: Number,
            private val max: Number,
            private val optimum: Number
        ) :
            BarChartMode {
            override fun min(values: List<Int>): Number =
                this.min

            override fun low(values: List<Int>): Number =
                this.low

            override fun high(values: List<Int>): Number =
                this.high

            override fun max(values: List<Int>): Number =
                this.max

            override fun optimum(values: List<Int>): Number =
                this.optimum

        }
    }
}

fun <T> ContainerBuilder.barChart(
    title: String,
    factor: (T) -> Int,
    values: Map<String, T>,
    unit: String = "",
    infoFn: (T) -> String = { "$it $unit" },
    mode: BarChartMode = BarChartSmallerBetter()
) {
    content.add { BarChart(title, values, unit, infoFn, factor, mode) }
}


object BarChartHtmlRenderer : Renderer<BarChart<Any>> {

    override val mode: RenderMode = Html

    override fun render(content: BarChart<Any>): String {

        val values = content.values.values.map { content.factor(it) }

        val rows = content.values.map { (label, value) ->
            val id = "${content.title}_$label".asKey()
            val info = content.infoFn(value)
            """<label for="$id">$label</label>
              |<meter id="$id"
              |       min="${content.mode.min(values)}"
              |       low="${content.mode.low(values)}"
              |       high="${content.mode.high(values)}"
              |       max="${content.mode.max(values)}"
              |       optimum="${content.mode.optimum(values)}"
              |       value="${content.factor(value)}"
              |       title="${content.unit}">$info</meter>
              |<div class="info">$info</div>"""
        }.joinToString("\n")

        return """<div class="charts bar">
                 |  <div class="body">$rows</div>
                 |  <div class="caption">${content.title}</div>
                 |</div>""".trimMargin()
    }
}

object BarChartTextRenderer : Renderer<BarChart<Any>> {

    override val mode: RenderMode = Text

    override fun render(content: BarChart<Any>): String =
        content.title
}