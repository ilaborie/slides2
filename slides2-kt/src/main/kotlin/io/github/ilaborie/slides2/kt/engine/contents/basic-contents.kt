package io.github.ilaborie.slides2.kt.engine.contents

import io.github.ilaborie.slides2.kt.engine.ContainerContent
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.SingleContent
import io.github.ilaborie.slides2.kt.jvm.tools.MarkdownToHtml.markdownToHtml

data class TextContent(
    val text: String,
    val escape: Boolean = true
) : Content

data class CompoundContent(override val inner: List<Content>) : ContainerContent {
    override val classes: Set<String>
        get() = emptySet()

    override fun toString(): String =
        "CompoundContent"
}

data class Title(
    val level: Int,
    override val content: Content,
    override val classes: Set<String>
) : SingleContent {
    init {
        require(level in 1..6) { "Expected a level in 1..6, got $level" }
    }
}

data class MarkdownContent(val md: String) : Content {
    val html: String by lazy {
        markdownToHtml(md)
    }
}

data class Paragraph(
    override val content: Content,
    override val classes: Set<String>
) : SingleContent

enum class TextStyle { Span, Strong, Emphasis, UnderLine, Mark, Keyboard, Pre, Ins, Del, Sub, Sup }
data class StyledText(
    val style: TextStyle,
    override val content: Content, override val classes: Set<String>
) : SingleContent

data class UnorderedList(
    override val inner: List<Content>,
    override val steps: Boolean = false,
    override val classes: Set<String>
) : ContainerContent

data class OrderedList(
    override val inner: List<Content>,
    override val steps: Boolean = false,
    override val classes: Set<String>
) : ContainerContent

data class Code(val language: String?, val code: String) : Content

data class Link(
    val href: String,
    override val content: Content, override val classes: Set<String>
) : SingleContent

data class Quote(
    val author: String?,
    val cite: String?,
    override val content: Content,
    override val classes: Set<String>
) : SingleContent

enum class NoticeKind { Tips, Info, Warning, Danger }
data class Notice(
    val kind: NoticeKind,
    val title: String?,
    override val content: Content,
    override val classes: Set<String>
) : SingleContent

data class Figure(
    val title: String,
    val src: String,
    val copyright: Content?,
    override val classes: Set<String>
) : ContainerContent {
    override val inner: List<Content> =
        copyright?.let { listOf(it) } ?: emptyList()
}


typealias TermAndDefinitions = Pair<Content, List<Content>>

data class Definitions(val definitions: List<TermAndDefinitions>, override val classes: Set<String>) :
    ContainerContent {
    override val inner: List<Content>
        get() = definitions.flatMap { listOf(it.first) + it.second }
}