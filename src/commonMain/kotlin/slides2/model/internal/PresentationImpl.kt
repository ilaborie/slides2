package slides2.model.internal

import slides2.io.Folder
import slides2.model.Content
import slides2.model.Part
import slides2.model.Presentation
import slides2.model.RenderMode


class PresentationImpl() : Presentation {
    override suspend fun render(rendering: RenderMode, dest: Folder): String {
        TODO("not implemented")
    }

    override val id: String
        get() = TODO("not implemented")

    override val title: Content
        get() = TODO("not implemented")

    override val styles: Set<String>
        get() = TODO("not implemented")

    override val scripts: Set<String>
        get() = TODO("not implemented")

    override val parts: List<Part>
        get() = TODO("not implemented")

}