package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.module


object TocPlugin : WebPlugin {
    override val name = "Table Of Content"

    override fun scripts(): List<Script> =
        listOf(module("toc.js"))
}

object GridPlugin : WebPlugin {
    override val name = "Grid navigation"

    override fun scripts(): List<Script> =
        listOf(module("slide.js"), module("grid.js"))
}

object NavigatePlugin : WebPlugin {
    override val name = "JavaScript Navigation"

    override fun scripts(): List<Script> =
        listOf(
            module("navigate.js"),
            module("slide.js")
        )
}
