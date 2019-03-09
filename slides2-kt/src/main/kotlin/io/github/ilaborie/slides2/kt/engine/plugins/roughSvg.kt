package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Script


object RoughSvgPlugin : WebPlugin {

    override val name = "Rough inline SVG"

    override fun scripts(): List<Script> =
        listOf(
            Script("$cloudfare/rough.js/3.0.0/rough.js", async = false, module = false, defer = false),
            Script("./rough-svg.js", async = false, module = false, defer = false)
        )
}
