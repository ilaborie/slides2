package io.github.ilaborie.slides2.kt.engine.contents

import io.github.ilaborie.slides2.kt.engine.Content


data class Text(val text: String) : Content

data class Title(val level: Int, val content: Content) : Content {
    init {
        require(level in 1..6) { "Expected a level in 1..6, got $level" }
    }
}

data class Paragraph(val content: Content) : Content

enum class TextStyle { Strong, Emphasis, UnderLine, Mark, Keyboard, Pre }
data class StyledText(val style: TextStyle, val content: Content) : Content

