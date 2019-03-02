package io.github.ilaborie.slides2.kt.engine.extra

import io.github.ilaborie.slides2.kt.SlideEngine.findRenderer
import io.github.ilaborie.slides2.kt.cli.Notifier.warning
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.contents.raw
import io.github.ilaborie.slides2.kt.engine.renderers.PresentationHtmlRenderer
import io.github.ilaborie.slides2.kt.engine.renderers.Script

const val cloudfare = "https://cdnjs.cloudflare.com/ajax/libs"

// See https://prismjs.com/
fun usePrismJs(
    showLines: Boolean = true,
    theme: String? = null,
    prismVersion: String = "1.15.0",
    languages: List<String> = emptyList() // see <https://prismjs.com/#languages-list>
): Renderer<Presentation> =
    findRenderer(Html, Presentation("dummy".raw))
        ?.let {
            fun String.script(): Script =
                Script(
                    this,
                    async = false,
                    module = false,
                    defer = false
                )

            val scripts =
                listOf("$cloudfare/prism/$prismVersion/prism.min.js") +
                        (if (showLines) listOf("$cloudfare/prism/$prismVersion/plugins/line-numbers/prism-line-numbers.min.js")
                        else emptyList()) +
                        languages.map { lang ->
                            "$cloudfare/prism/$prismVersion/components/prism-$lang.min.js"
                        }
            when (it) {
                is PresentationHtmlRenderer ->
                    PresentationHtmlRenderer(
                        scripts = it.scripts + scripts.map { it.script() },
                        stylesheets = it.stylesheets +
                                "$cloudfare/prism/$prismVersion/themes/prism.min.css" +
                                (if (theme != null) listOf("$cloudfare/prism/$prismVersion/themes/prism-$theme.min.css")
                                else emptyList()) +
                                if (showLines) listOf("$cloudfare/prism/$prismVersion/plugins/line-numbers/prism-line-numbers.min.css") else emptyList()
                    )
                else                                                                       ->
                    it.also {
                        warning {
                            "Cannot add PrismJS, expected a `PresentationHtmlRenderer`, got $it"
                        }
                    }
            }
        }
        ?: throw IllegalStateException("Missing the Presentation HTML renderer")

