package io.github.ilaborie.slides2.kt.engine.tools

import io.github.ilaborie.slides2.kt.jvm.tools.MarkdownToHtml


fun main() {
    val md = """A markdown string
        |
        |* list 1
        |* list 2
        |
        |**string**, _underline_, ~~stroke~~
        |
        |```kotlin
        |println("Hello World !")
        |```
        |
        |> cite
        |> -- by who
        |
        | [A Link](http://example.com)
        |
    """.trimMargin()


    val html = MarkdownToHtml.markdownToHtml(md)

    println(html)
}