package io.github.ilaborie.slides2.kt.engine


interface Renderer<T:Content> {
    val mode: RenderMode

    fun render(content: T): String

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
