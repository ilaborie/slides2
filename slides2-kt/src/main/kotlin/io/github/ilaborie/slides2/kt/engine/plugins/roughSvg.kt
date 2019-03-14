package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.module
import io.github.ilaborie.slides2.kt.engine.Script.Companion.unpkg


object RoughSvgPlugin : WebPlugin {

    override val name = "Rough inline SVG"

    override fun scripts(): List<Script> =
        listOf(
            module(unpkg("roughjs", path = "/dist/rough.umd.js")),
            module("./rough-svg.js")
        )
}
