package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Script


object TocPlugin : WebPlugin {
    override val name = "Table Of Content"

    override fun scripts(): List<Script> =
        listOf(Script("./toc.js"))
}

object NavigatePlugin : WebPlugin {
    override val name = "JavaScript Navigation"

    override fun scripts(): List<Script> =
        listOf(Script("./navigate.js"))
}
