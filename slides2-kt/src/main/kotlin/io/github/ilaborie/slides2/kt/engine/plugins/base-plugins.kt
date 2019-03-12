package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.module


object TocPlugin : WebPlugin {
    override val name = "Table Of Content / Grid"

    override fun scripts(): List<Script> =
        listOf(module("./toc.js"))
}

object NavigatePlugin : WebPlugin {
    override val name = "JavaScript Navigation"

    override fun scripts(): List<Script> =
        listOf(
            module("./navigate.js"),
            module("./slide.js")
        )
}
