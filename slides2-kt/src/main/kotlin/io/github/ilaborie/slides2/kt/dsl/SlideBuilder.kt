package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Slide
import io.github.ilaborie.slides2.kt.engine.contents.ol
import io.github.ilaborie.slides2.kt.engine.contents.p
import io.github.ilaborie.slides2.kt.engine.contents.ul

@PresentationMarker
class SlideBuilder(internal val index: Int, internal val partDsl: PartBuilder) {

    private val content: MutableList<() -> Content> = mutableListOf()

    internal fun build(id: Id, title: Content, styles: Set<String>): Slide =
        Slide(
            id = id,
            title = title,
            styles = styles,
            content = content.map { it() }
        )

    fun p(text: String) {
        content.add { text.p }
    }

    fun ol(list: List<Content>) {
        content.add { list.ol }
    }

    fun ul(list: List<Content>) {
        content.add { list.ul }
    }

}
