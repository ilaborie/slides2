package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.script


object RoughSvgPlugin : WebPlugin {

    override val name = "Rough inline SVG"

    override fun scripts(): List<Script> =
        listOf(
            script("$cloudfare/rough.js/3.0.0/rough.js"),
            script("./rough-svg.js")
        )
}
