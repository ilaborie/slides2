package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.SlideEngine


fun main() {

    SlideEngine
        .registerRenderer<Presentation>(PresentationRendererHtml())

    val presentation = Presentation(
        title = Title("Test")
    )

    with(SlideEngine) {

    }

}