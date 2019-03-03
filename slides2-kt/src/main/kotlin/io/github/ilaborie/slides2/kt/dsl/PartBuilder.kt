package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Part
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.engine.Slide
import io.github.ilaborie.slides2.kt.engine.contents.OrderedList
import io.github.ilaborie.slides2.kt.engine.contents.h3
import io.github.ilaborie.slides2.kt.jvm.asKey

@PresentationMarker
class PartBuilder(internal val presentationDsl: PresentationBuilder) {

    private val slides: MutableList<LazyBuilder<Slide>> = mutableListOf()

    internal fun build(id: Id, title: Content, style: String?, skipHeader: Boolean): Part =
        Part(
            id = id,
            title = title,
            style = style,
            skipHeader = skipHeader,
            slides = slides.map { it.builder() }
        )

    fun slide(
        title: String,
        styles: Set<String> = emptySet(),
        id: String = title.asKey(),
        block: SlideBuilder.() -> Unit
    ) {
        slide(slideTitle = title.h3, id = id, styles = styles, block = block)
    }

    fun slide(
        slideTitle: Content,
        styles: Set<String> = emptySet(),
        id: String = with(SlideEngine) { render(Text, slideTitle) },
        block: SlideBuilder.() -> Unit
    ) {
        val slideId = Id(id)
        slides.add(LazyBuilder(slideId, slideTitle) {
            SlideBuilder(this)
                .apply(block)
                .build(slideId, slideTitle, styles)
        })
    }

    fun roadmap(title: String) {
        val dsl = presentationDsl
        slide(title, styles = setOf("roadmap")) {
            content.add {
                OrderedList(inner = dsl.parts.map { it.title }, classes = emptySet())
            }
        }
    }
}
