package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.async
import io.github.ilaborie.slides2.kt.engine.Script.Companion.cloudfare


// See https://www.mathjax.org/

class MathJaxPlugin(
    private val version: String = "2.7.5",
    private val config: String = "TeX-MML-AM_CHTML"
) : WebPlugin {

    override val name = "Beautiful math with MathJax"

    override fun scripts(): List<Script> =
        listOf(
            async("$cloudfare/mathjax/$version/MathJax.js?config=$config")
                .copy(cacheLocal = false)
        )
}

fun ContainerBuilder.asciiMath(block: () -> String) {
    span(setOf("math-ascii")) {
        html{ "`${block()}`"}
    }
}