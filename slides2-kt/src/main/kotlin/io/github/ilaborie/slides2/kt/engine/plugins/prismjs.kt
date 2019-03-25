package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.cloudfare
import io.github.ilaborie.slides2.kt.engine.Script.Companion.script
import io.github.ilaborie.slides2.kt.engine.Stylesheet


// See https://prismjs.com/
class PrismJsPlugin(
    private val languages: List<String> = emptyList(), // see <https://prismjs.com/#languages-list>
    private val theme: String? = null,
    private val version: String = "1.15.0",
    private val showLines: Boolean = true
) : WebPlugin {
    override val name = "PrismJs code highlighting"

    private val plugins: List<String> =
        if (showLines) listOf("line-numbers") else emptyList()

    override fun scripts(): List<Script> =
        listOf(script("$cloudfare/prism/$version/prism.min.js")) +
                // Languages
                languages.map { script("$cloudfare/prism/$version/components/prism-$it.min.js") } +
                // plugins
                plugins.map { script("$cloudfare/prism/$version/plugins/$it/prism-$it.min.js") }
                // Extra show Line
//                (if (showLines) listOf(script("line-numbers.js")) else emptyList())

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
