package io.github.ilaborie.slides2.kt.engine.contents

import io.github.ilaborie.slides2.kt.engine.ContainerContent
import io.github.ilaborie.slides2.kt.engine.Content

data class Text(val text: String, val escape: Boolean = true) : Content

data class Title(val level: Int, val content: Content) : ContainerContent {
    init {
        require(level in 1..6) { "Expected a level in 1..6, got $level" }
    }

    override val inner: kotlin.collections.List<Content>
        get() = listOf(content)
}

data class Paragraph(val content: Content) : ContainerContent {

    override val inner: kotlin.collections.List<Content>
        get() = listOf(content)
}

enum class TextStyle { Strong, Emphasis, UnderLine, Mark, Keyboard, Pre }
data class StyledText(val style: TextStyle, val content: Content) : ContainerContent {

    override val inner: kotlin.collections.List<Content>
        get() = listOf(content)
}

data class UnorderedList(override val inner: List<Content>) : ContainerContent
data class OrderedList(override val inner: List<Content>) : ContainerContent