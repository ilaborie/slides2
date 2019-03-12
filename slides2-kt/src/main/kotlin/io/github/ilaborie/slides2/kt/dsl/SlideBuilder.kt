package io.github.ilaborie.slides2.kt.dsl

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Id
import io.github.ilaborie.slides2.kt.engine.Slide

@PresentationMarker
class SlideBuilder(partDsl: PartBuilder) : ContainerBuilder(partDsl.presentationDsl.input) {

    internal fun build(id: Id, title: Content, styles: Set<String>): Slide {
        val contents = super.build()
        return Slide(
            id = id,
            title = title,
            classes = styles + (if (contents.any { it.steps }) setOf("steps") else emptySet()),
            content = contents
        )
    }

}
