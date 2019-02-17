```kotlin

// content.kt
interface Content : CanRender

// render.kt
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


// Slides
interface Presentation : CanRender {
    val id: String
    val title: Content
    val styles: Set<String>
    val scripts: Set<String>

    val parts: List<Part>
}

interface Part : CanRender {
    val id: String
    val title: Content
    val styles: Set<String>
    val headerSlide: Slide?

    val slides: List<Slide>
}

interface Slide : CanRender {
    val id: String
    val title: Content
    val styles: Set<String>

    val content: Content
}
```