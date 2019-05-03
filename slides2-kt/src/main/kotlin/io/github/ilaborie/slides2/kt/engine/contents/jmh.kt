package io.github.ilaborie.slides2.kt.engine.contents

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.raw
import io.github.ilaborie.slides2.kt.engine.ContainerContent
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.renderers.asHtmlClass


data class Benchmark(
    val name: String,
    val score: Double,
    val error: Double,
    val mode: String = "thrpt",
    val unit: String = "ops/s",
    val cnt: Int = 200
) {
    override fun toString(): String =
        "$name: $score $unit ±$error"
}

data class JmhBenchmark(
    val title: String,
    val benchmarks: List<Benchmark>,
    val extraClasses: Set<String>
) : ContainerContent {

    override val classes: Set<String>
        get() = extraClasses + "jmh"

    override val inner: List<Content> by lazy {
        benchmarks.map { it.toString().raw}
    }
}


fun ContainerBuilder.jmh(
    benchmarks: List<Benchmark>,
    classes: Set<String> = emptySet(),
    title: String = "JMH benchmarks"
) {
    content.add {
        JmhBenchmark(title,benchmarks, classes)
    }
}

object JmhBenchmarkTextRenderer : Renderer<JmhBenchmark> {
    override val mode = Text

    override fun render(content: JmhBenchmark): String =
        content.benchmarks.joinToString("\n")
}


object JmhBenchmarkHtmlRenderer : Renderer<JmhBenchmark> {
    override val mode = Html

    override fun render(content: JmhBenchmark): String =
        with(SlideEngine) {
            val tbodyRow = { bench: Benchmark ->
                """
                  |<th>${bench.name}</th>
                  |<td>${bench.mode}</td>
                  |<td>${bench.cnt}</td>
                  |<td>${bench.score}</td>
                  |<td>${bench.error}</td>
                  |<td>${bench.unit}</td>
                """.trimMargin()
            }

            """<table${content.classes.asHtmlClass}>
              |  <thead>
              |    <tr>
              |      <th>Benchmark</th>
              |      <th>Mode</th>
              |      <th>Cnt</th>
              |      <th>Score</th>
              |      <th>Error</th>
              |      <th>Units</th>
              |    </tr>
              |  </thead>
              |  <tbody>
              |    ${content.benchmarks.joinToString("</tr><tr>", "<tr>", "</tr>") { tbodyRow(it) }}
              |  </tbody>
              |  <caption>${content.title}</caption>
              |</table>""".trimMargin()
        }
}