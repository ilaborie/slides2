package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Slide
import io.github.ilaborie.slides2.kt.engine.contents.p

@PresentationMarker
class SlideBuilder(internal val partDsl: PartBuilder) {

    private var content: List<LazyContent> = emptyList()

    internal fun build(title: Content, styles: Set<String>): Slide =
        Slide(
            title = title,
            styles = styles,
            content = content.map { it() }
        )

    fun p(text: String) {
        content = content + { text.p }
    }

}
