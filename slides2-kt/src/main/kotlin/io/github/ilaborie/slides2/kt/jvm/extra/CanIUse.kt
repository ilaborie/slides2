package io.github.ilaborie.slides2.kt.jvm.extra

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
    val browsers: List<Browser>
) : Content {

    private val mapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .findAndRegisterModules()

    private val cachingFolder: CachingFolder = CachingFolder(JvmFolder(".cache/caniuse")) { feature ->
        URL("https://raw.githubusercontent.com/Fyrd/caniuse/master/features-json/$feature.json")
            .readText()
    }


    val content: Map<String, Map<Browser, String>> by lazy {
        features
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
                    Triple(featureData.title, browser, status)
                }
            }.groupBy { it.first }
            .mapValues { (_, list) ->
                list.map { (_, browser, status) -> browser to status }
                    .toMap()
            }
    }
}

fun ContainerBuilder.caniuse(title: String, features: List<String>, browsers: List<Browser>) {
    content.add { CanIUse(title, features, browsers) }
}

object CanIUseHtmlRenderer : Renderer<CanIUse> {
    override val mode: RenderMode = Html

    override fun render(content: CanIUse): String {
        val tbody = content.content
            .map { (feature, map) ->
                val values = content.browsers.joinToString("") { browser ->
                    """<td class="${map[browser] ?: "notFound"}"></td>"""
                }
                """<th>$feature</th>$values"""
            }.joinToString("</tr><tr>", "<tr>", "</tr>")

        return """<table class="caniuse">
            |<caption>${content.title}</caption>
            |<thead>
            |<tr>
            | <td></td>
            | ${content.browsers.joinToString("</th><th>", "<th>", "</th>") { (name, version) ->
            "$name v$version"
        }}
            |</tr>
            |</thead>
            |<tbody>
            |$tbody
            |</tbody>
            |</table>
            |<ul class="list-inline caniuse">
            |<li class="y">Supported</li>
            |<li class="p">Partial Support</li>
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

