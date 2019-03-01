package io.github.ilaborie.slides2.kt.engine.renderers

import io.github.ilaborie.slides2.kt.SlideEngine.findRenderer
import io.github.ilaborie.slides2.kt.SlideEngine.notifier
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.contents.raw

const val cloudfare = "https://cdnjs.cloudflare.com/ajax/libs/"

// See https://prismjs.com/
fun usePrismJs(
    theme: String? = null,
    prismVersion: String = "1.15.0",
    languages: List<String> = emptyList() // see <https://prismjs.com/#languages-list>
): Renderer<Presentation> =
    findRenderer(Html, Presentation("dummy".raw))
        ?.let {
            when (it) {
                is PresentationHtmlRenderer ->
                    PresentationHtmlRenderer(
                        scripts = it.scripts +
                                "$cloudfare/prism/$prismVersion/prism.min.js"
                                + languages.map { lang ->
                            "$cloudfare/prism/$prismVersion/components/prism-$lang.min.js"
                        }
                        ,
                        stylesheets = it.stylesheets +
                                "$cloudfare/prism/$prismVersion/themes/prism.min.css"
                                + if (theme != null) listOf("$cloudfare/prism/$prismVersion/themes/prism-$theme.min.css")
                        else emptyList()
                    )
                else                        ->
                    it.also {
                        notifier.warning {
                            "Cannot add PrismJS, expected a `PresentationHtmlRenderer`, got $it"
                        }
                    }
            }
        }
        ?: throw IllegalStateException("Missing the Presentation HTML renderer")

