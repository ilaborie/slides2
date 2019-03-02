package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.cli.Notifier.warning
import io.github.ilaborie.slides2.kt.cli.Styles
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.contents.Title


// FIXME do a composable functional checker
object CheckContentPlugin : ContentPlugin {

    override val name: String = "Check content plugin"

    override fun invoke(content: Content): Content =
        when (content) {
            is Presentation -> check(content)
            else            -> content
        }

    private fun check(presentation: Presentation): Presentation {
        val title = presentation.title
        when (title) {
            is Title ->
                if (title.level != 1) {
                    warning {
                        "Expected a `${Styles.highlight("H1")}` for presentation title, got H${title.level}"
                    }
                }
            else     ->
                warning {
                    "Expected a `${Styles.highlight("Title")}` for presentation title, got $title"
                }
        }

        return presentation
    }


}