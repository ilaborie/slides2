package io.github.ilaborie.slides2.kt.jvm.extra

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.utils.CachingFolder
import java.net.URL


typealias Browser = Pair<String, Int>
typealias BrowserFeatureData = Map<String, String> // version: support

data class FeatureData(
    val title: String,
    val description: String,
    val spec: String,
    val stats: Map<String, BrowserFeatureData>
)

data class CanIUse(
    val title: String,
    val features: List<String>,
    val browsers: List<Browser>,
    val dataFn: (() -> Map<FeatureData, Map<Browser, String>>?) = { null },
    val featureFn: (FeatureData) -> Content,
    val browserFn: (Browser) -> Content,
    val statusFn: (String) -> Content
) : Content {

    private val mapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .findAndRegisterModules()

    private val cachingFolder: CachingFolder = CachingFolder(JvmFolder(".cache/caniuse")) { feature ->
        URL("https://raw.githubusercontent.com/Fyrd/caniuse/master/features-json/$feature.json")
            .readText()
    }


    val content: Map<FeatureData, Map<Browser, String>> by lazy {
        dataFn() ?: features
            .map { cachingFolder[it] }
            .map { mapper.readValue<FeatureData>(it) }
            .flatMap { featureData ->
                browsers.map { browser ->
                    val (name, version) = browser
                    val status = featureData.stats[name]
                        ?.get(version.toString())
                        ?.first()
                        ?.toString()
                        ?: "notFound"
                    Triple(featureData, browser, status)
                }
            }.groupBy { it.first }
            .mapValues { (_, list) ->
                list.map { (_, browser, status) -> browser to status }
                    .toMap()
            }
    }
}

fun ContainerBuilder.caniuse(
    title: String,
    features: List<String>,
    browsers: List<Browser>,
    dataFn: (() -> Map<FeatureData, Map<Browser, String>>?) = { null },
    featureFn: (FeatureData) -> ContainerBuilder.() -> Unit = { feature -> { html { feature.title } } },
    browserFn: (Browser) -> ContainerBuilder.() -> Unit = { (name, version) -> { html { "$name $version" } } },
    statusFn: (String) -> ContainerBuilder.() -> Unit = { status -> { html { status } } }

) {
    content.add {
        CanIUse(title, features, browsers, dataFn,
                { ContainerBuilder(input).compound(featureFn(it)) },
                { ContainerBuilder(input).compound(browserFn(it)) },
                { ContainerBuilder(input).compound(statusFn(it)) })
    }
}

object CanIUseHtmlRenderer : Renderer<CanIUse> {
    override val mode: RenderMode = Html

    override fun render(content: CanIUse): String =
        with(SlideEngine) {
            val tbody = content.content
                .map { (feature, map) ->
                    val values = content.browsers.joinToString("") { browser ->
                        val status = map[browser] ?: "notFound"
                        """<td class="$status">${render(mode, content.statusFn(status))}</td>"""
                    }
                    """<th>${render(mode, content.featureFn(feature))}</th>$values"""
                }.joinToString("</tr><tr>", "<tr>", "</tr>")

            """<table class="caniuse">
            |<caption>${content.title}</caption>
            |<thead>
            |<tr>
            | <td></td>
            | ${content.browsers.joinToString("</th><th>", "<th>", "</th>") {
                render(mode, content.browserFn(it))
            }}
            |</tr>
            |</thead>
            |<tbody>
            |$tbody
            |</tbody>
            |</table>
            |<ul class="list-inline caniuse legend">
            |<li class="y">Supported</li>
            |<li class="a">Partial Support</li>
            |<li class="n">Not Supported</li>
            |</ul>""".trimMargin()
        }
}

object CanIUseTextRenderer : Renderer<CanIUse> {
    override val mode: RenderMode = Text
    override fun render(content: CanIUse): String =
        content.content
            .toList()
            .flatMap { (feature, map) ->
                map.map { (browser, state) -> Triple(feature, browser, state) }
            }
            .joinToString("\n") { (feature, browser, status) ->
                val (name, version) = browser
                "$feature with $name v$version : $status"
            }
}

