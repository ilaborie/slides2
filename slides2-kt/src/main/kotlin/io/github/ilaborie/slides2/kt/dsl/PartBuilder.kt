package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Part
import io.github.ilaborie.slides2.kt.engine.Slide
import io.github.ilaborie.slides2.kt.engine.contents.Title
import io.github.ilaborie.slides2.kt.engine.contents.h3
import io.github.ilaborie.slides2.kt.jvm.asKey

@PresentationMarker
class PartBuilder(private val index: Int, internal val presentationDsl: PresentationBuilder) {

    private val slides: MutableList<LazyBuilder<Slide>> = mutableListOf()

    internal fun build(id: Id, title: Title, style: String?): Part =
        Part(
            id = id,
            title = title,
            style = style,
            slides = slides.map { it.builder() }
        )

    fun slide(
        title: String,
        styles: Set<String> = emptySet(),
        block: SlideBuilder.() -> Unit
    ) {
        val slideTitle = title.h3
        val slideIndex = slides.size + 1
        val id = Id("${index}_${slideIndex}_${title.asKey()}")
        slides.add(LazyBuilder(id, slideTitle) {
            SlideBuilder(slideIndex, this)
                .apply(block)
                .build(id, slideTitle, styles)
        })
    }

    fun roadmap(title: String) {
        slide(title,styles = setOf("roadmap")) {
            ol {
                this@PartBuilder.presentationDsl
                    .parts
                    .map { it.title }
            }
        }
    }
}
