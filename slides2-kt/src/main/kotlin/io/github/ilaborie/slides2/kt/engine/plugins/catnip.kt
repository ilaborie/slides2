package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.raw
import io.github.ilaborie.slides2.kt.engine.Script.Companion.script
import io.github.ilaborie.slides2.kt.engine.Stylesheet

class CatnipPlugin(private val selector: String = ".catnip") : WebPlugin {
    override val name = "Catnip Java Bytecode interpretor"

    // See https://github.com/ilaborie/catnip
    private val github = "https://raw.githubusercontent.com/ilaborie/catnip/gh-pages"

    override fun scripts(): List<Script> =
        listOf(
            script("$github/catnip.0478872c.js"),
            raw {
                """document.querySelectorAll('$selector')
                  | .forEach(elt => catnip(elt));""".trimMargin()
            }
        )

    override fun stylesheets(): List<Stylesheet> =
        listOf(Stylesheet("$github/catnip.42b131b3.css"))
}
