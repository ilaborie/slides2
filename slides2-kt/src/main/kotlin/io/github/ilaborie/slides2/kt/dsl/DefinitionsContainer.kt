package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.contents.TermAndDefinitions

@PresentationMarker
open class DefinitionsContainer(internal val input: Folder) {

    internal val content: MutableList<() -> TermAndDefinitions> = mutableListOf()

    fun term(term: String, block: ContainerBuilder.() -> Unit) {
        content.add {
            val builder = ContainerBuilder(input)
            builder.apply(block)
            val list = builder.content.map { it() }
            term.raw to list
        }
    }

    fun term(termContent: Content, block: ContainerBuilder.() -> Unit) {
        content.add {
            val builder = ContainerBuilder(input)
            builder.apply(block)
            val lst = builder.content.map { it() }
            termContent to lst
        }
    }
}