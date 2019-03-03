package io.github.ilaborie.slides2.kt.engine.extra

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Stylesheet

// See https://prismjs.com/
const val cloudfare = "https://cdnjs.cloudflare.com/ajax/libs"


private fun String.script(): Script =
    Script(
        this,
        async = false,
        module = false,
        defer = false
    )

fun SlideEngine.usePrismJs(
    showLines: Boolean = true,
    theme: String? = null,
    version: String = "1.15.0",
    languages: List<String> = emptyList() // see <https://prismjs.com/#languages-list>
) {
    // Main
    globalScripts += "$cloudfare/prism/$version/prism.min.js".script()
    globalStylesheets += Stylesheet("$cloudfare/prism/$version/themes/prism.min.css")

    // Theme
    if (theme != null) {
        globalStylesheets += Stylesheet("$cloudfare/prism/$version/themes/prism-$theme.min.css")
    }

    // Languages
    globalScripts += languages.map { lang ->
        "$cloudfare/prism/$version/components/prism-$lang.min.js".script()
    }

    // Plugins
    if (showLines) {
        val plugin = "line-numbers"
        globalScripts += "$cloudfare/prism/$version/plugins/$plugin/prism-$plugin.min.js".script()
        globalStylesheets += Stylesheet("$cloudfare/prism/$version/plugins/$plugin/prism-$plugin.min.css")
    }
}
