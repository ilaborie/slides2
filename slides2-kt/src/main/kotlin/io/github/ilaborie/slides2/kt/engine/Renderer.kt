package io.github.ilaborie.slides2.kt.engine


interface Renderer {
    val mode: RenderMode

    fun render(): String

    companion object {

        enum class RenderMode {
            Html,
            Text,
//    Reveal
//    Markdown,
//    Bespoke,
//    GoogleSlide, ...
        }

    }
}
