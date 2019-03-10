package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.script
import io.github.ilaborie.slides2.kt.engine.Stylesheet

const val cloudfare = "https://cdnjs.cloudflare.com/ajax/libs"


// See https://prismjs.com/
class PrismJsPlugin(
    val languages: List<String> = emptyList(), // see <https://prismjs.com/#languages-list>
    val theme: String? = null,
    val version: String = "1.15.0",
    showLines: Boolean = true
) : WebPlugin {
    override val name = "PrismJs code highlighting"

    private val plugins: List<String> =
        if (showLines) listOf("line-numbers") else emptyList()

    override fun scripts(): List<Script> =
        (listOf("$cloudfare/prism/$version/prism.min.js") +
                // Languages
                languages.map { "$cloudfare/prism/$version/components/prism-$it.min.js" } +
                // plugins
                plugins.map { "$cloudfare/prism/$version/plugins/$it/prism-$it.min.js" }
                )
            .map { script(it) }

    override fun stylesheets(): List<Stylesheet> =
        (listOf("$cloudfare/prism/$version/themes/prism.min.css") +
                // Theme
                (if (theme != null)
                    listOf("$cloudfare/prism/$version/themes/prism-$theme.min.css")
                else emptyList()) +
                // Plugins
                plugins.map { "$cloudfare/prism/$version/plugins/$it/prism-$it.min.css" })
            .map { Stylesheet(it) }

}
