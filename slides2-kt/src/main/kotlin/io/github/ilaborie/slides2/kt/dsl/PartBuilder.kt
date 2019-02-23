package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.engine.Part
import io.github.ilaborie.slides2.kt.engine.Slide
import io.github.ilaborie.slides2.kt.engine.contents.Title
import io.github.ilaborie.slides2.kt.engine.contents.h3
import io.github.ilaborie.slides2.kt.engine.contents.ol

@PresentationMarker
class PartBuilder(internal val presentationDsl: PresentationBuilder) {

    internal val slides: MutableList<LazySlide> = mutableListOf()

    internal fun build(title: Title, style: String?): Part =
        Part(
            title = title,
            style = style,
            slides = slides.map { it.second() }
        )

    fun slide(
        title: String,
        styles: Set<String> = emptySet(),
        block: SlideBuilder.() -> Unit
    ) {
        val slideTitle = title.h3
        slides.add(Info(slideTitle) to {
            SlideBuilder(this)
                .apply(block)
                .build(slideTitle, styles)
        })
    }

    fun roadmap(title: String) {
        val roadmapTitle = title.h3
        slides.add(Info(roadmapTitle) to {
            Slide(
                title = title.h3,
                styles = setOf("roadmap"),
                content =
                listOf(
                    presentationDsl.parts
                        .map { it.first.title }
                        .ol
                )
            )
        })
    }

}
