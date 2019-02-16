package slides2.model

import slides2.io.Folder


enum class RenderMode {
    Html,
    Markdown,
    Text,
    Bespoke,
    Reveal
    // GoogleSlide, ...
}


interface CanRender {
    suspend fun render(mode: RenderMode, dest: Folder): String
}