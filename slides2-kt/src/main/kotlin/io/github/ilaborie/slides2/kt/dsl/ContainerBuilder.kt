package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.Folder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.contents.*
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Del
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Emphasis
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Ins
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Keyboard
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Mark
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Pre
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Span
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Strong
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Sub
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Sup
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.UnderLine
import io.github.ilaborie.slides2.kt.term.Notifier
import io.github.ilaborie.slides2.kt.term.Styles

@PresentationMarker
open class ContainerBuilder(internal val input: Folder) {

    internal val content: MutableList<() -> Content> = mutableListOf()

    fun build(): List<Content> =
        content.map { it() }

    fun compound(block: ContainerBuilder.() -> Unit): Content {
        val builder = ContainerBuilder(input)
        builder.apply(block)
        val lst = builder.content.map { it() }
        return CompoundContent(lst)
    }

    fun file(file: String) {
        val extension = file.split(".").last()
        if (!input.exists(file)) {
            input.writeTextFile(file) { "TODO fill" }
            Notifier.warning {
                "No file ${Styles.highlight(file)}, it has been created with dummy content"
            }
        }
        val content = { input.readFileAsString(file) }
        when (extension) {
            "md"   -> markdown(content)
            "html" -> html(content)
            "svg"  -> html(content)
            else   ->
                throw IllegalArgumentException("Unexpected file type, only *.{md,html} are supported yet, got $file")
        }
    }

    fun markdown(md: () -> String) {
        content.add { MarkdownContent(md()) }
    }

    fun todo(html: () -> String) {
        span(html(), classes = setOf("todo"))
    }

    fun html(html: () -> String) {
        content.add { TextContent(html(), escape = false) }
    }

    fun h1(title: String, classes: Set<String> = emptySet()) {
        content.add { Title(1, title.raw, classes) }
    }

    fun h1(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(1, classes, block)
    }

    fun h2(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(2, title.raw, classes)
        }
    }

    fun h2(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(2, classes, block)
    }

    fun h3(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(2, title.raw, classes)
        }
    }

    fun h3(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(3, classes, block)
    }

    fun h4(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(4, title.raw, classes)
        }
    }

    fun h4(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(4, classes, block)
    }

    fun h5(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(5, title.raw, classes)
        }
    }

    fun h5(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(5, classes, block)
    }

    fun h6(title: String, classes: Set<String> = emptySet()) {
        content.add {
            Title(6, title.raw, classes)
        }
    }

    fun h6(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        title(6, classes, block)
    }

    fun styledText(style: TextStyle, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            StyledText(style, c, classes)
        }
    }

    fun strong(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Strong, classes, block)
    }

    fun strong(text: String, classes: Set<String> = emptySet()) {
        styledText(Strong, classes) { html { text } }
    }

    fun em(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Emphasis, classes, block)
    }

    fun u(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(UnderLine, classes, block)
    }

    fun mark(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Mark, classes, block)
    }

    fun kbd(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Keyboard, classes, block)
    }

    fun pre(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Pre, classes, block)
    }
    fun pre(text:String, classes: Set<String> = emptySet()) {
        styledText(Pre, classes) {
            html { text }
        }
    }

    fun ins(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Ins, classes, block)
    }

    fun del(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Del, classes, block)
    }

    fun sub(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Sub, classes, block)
    }

    fun sup(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Sup, classes, block)
    }

    fun span(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        styledText(Span, classes, block)
    }

    fun span(text: String, classes: Set<String> = emptySet()) {
        styledText(Span, classes) { html { text } }
    }

    fun sourceCode(file: String) {
        val extension = file.split(".").last()
        val language = when (extension) {
            "class.txt" -> "java"
            "dex.dump"  -> "java"
            "smali"     -> "java"
            "kt"        -> "kotlin"
            "ts"        -> "typescript"
            "tsx"       -> "typescript"
            "js"        -> "javascript"
            "sh"        -> "bash"
            "re"        -> "reason"
            else        -> extension
        }

        if (!input.exists(file)) {
            input.writeTextFile(file) { "TODO fill the source file $language" }
            Notifier.warning {
                "No file ${Styles.highlight(file)}, it has been created with dummy content"
            }
        }
        code(language) { input.readFileAsString(file) }
    }

    fun code(language: String, codeBlock: () -> String) {
        content.add { Code(language, codeBlock()) }
    }

    fun p(classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            Paragraph(ContainerBuilder(input).compound(block), classes)
        }
    }

    fun p(text: String, classes: Set<String> = emptySet()) {
        content.add { Paragraph(text.raw, classes) }
    }

    fun ol(steps: Boolean = false, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            OrderedList(
                steps = steps,
                classes = classes,
                inner = ContainerBuilder(input).apply(block).build()
            )
        }
    }

    fun ul(steps: Boolean = false, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            UnorderedList(
                steps = steps,
                classes = classes,
                inner = ContainerBuilder(input).apply(block).build()
            )
        }
    }

    fun link(href: String, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit = { html { href } }) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            Link(href = href, content = c, classes = classes)
        }
    }

    fun link(href: String, content: String) {
        link(href) { html { content } }
    }

    fun quote(
        author: String? = null,
        cite: String? = null,
        classes: Set<String> = emptySet(),
        block: ContainerBuilder.() -> Unit
    ) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            Quote(author = author, cite = cite, classes = classes, content = c)
        }
    }

    fun quote(
        quote: String,
        author: String? = null,
        cite: String? = null,
        classes: Set<String> = emptySet()
    ) {
        quote(author = author, cite = cite, classes = classes) {
            html { quote }
        }
    }

    fun notice(
        kind: NoticeKind,
        title: String? = null,
        classes: Set<String> = emptySet(),
        block: ContainerBuilder.() -> Unit
    ) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            Notice(kind = kind, title = title, classes = classes, content = c)
        }
    }

    fun figure(src: String, title: String, classes: Set<String> = emptySet(), copyrightBlock: Content? = null) {
        content.add {
            val figSrc = input.readFileAsDataUri(src)
            Figure(src = figSrc, title = title, copyright = copyrightBlock, classes = classes)
        }
    }

    private fun title(level: Int, classes: Set<String> = emptySet(), block: ContainerBuilder.() -> Unit) {
        content.add {
            val c = ContainerBuilder(input).compound(block)
            Title(level = level, classes = classes, content = c)
        }
    }

    fun title(level: Int, text: String) {
        title(level) { text.raw }
    }

    fun definitions(classes: Set<String> = emptySet(), block: DefinitionsContainer.() -> Unit) {
        content.add {
            val builder = DefinitionsContainer(input)
            builder.apply(block)
            val list = builder.content.map { it() }
            Definitions(list, classes)
        }
    }


}
