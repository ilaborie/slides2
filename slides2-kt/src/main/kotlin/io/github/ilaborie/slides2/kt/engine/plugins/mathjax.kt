package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.async
import io.github.ilaborie.slides2.kt.engine.Script.Companion.cloudfare
import io.github.ilaborie.slides2.kt.engine.Script.Companion.raw


// See https://www.mathjax.org/

class MathJaxPlugin(
    private val version: String = "2.7.5",
    private val config: String? = null
) : WebPlugin {

    override val name = "Beautiful math with MathJax"

    override fun scripts(): List<Script> =
        listOf(
            raw("text/x-mathjax-config") {
                config?:"""MathJax.Hub.Config({
                    jax: ["input/AsciiMath","output/CommonHTML"],
                    extensions: ["asciimath2jax.js"],
                    asciimath2jax: {
                        processClass: 'math-ascii',
                        skipTags: ["script","noscript","style","textarea","pre","code", "a", "p"]
                        }
                    });""".trimIndent()
            },
            (async("$cloudfare/mathjax/$version/MathJax.js") as Script.Companion.BaseScript)
                .copy(cacheLocal = false)
        )
}

fun ContainerBuilder.asciiMath(block: () -> String) {
    span(setOf("math-ascii")) {
        html{ "`${block()}`"}
    }
}