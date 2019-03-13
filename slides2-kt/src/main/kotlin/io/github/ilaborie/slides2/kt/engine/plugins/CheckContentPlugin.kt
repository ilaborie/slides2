package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Presentation
import io.github.ilaborie.slides2.kt.engine.contents.Title
import io.github.ilaborie.slides2.kt.term.Notifier
import io.github.ilaborie.slides2.kt.term.Notifier.warning
import io.github.ilaborie.slides2.kt.term.Styles


// FIXME do a composable functional checker
object CheckContentPlugin : ContentPlugin {

    override val name: String = "Check content plugin"

    override fun invoke(content: Content): Content =
        when (content) {
            is Presentation -> check(content)
            else            -> content
        }

    private fun check(presentation: Presentation): Presentation {
        // Check title
        val title = presentation.title.flatten()
            .filterIsInstance<Title>()
            .firstOrNull { it.level == 1 }

        if (title == null) {
            warning {
                "Expected a `${Styles.highlight("H1")}` for presentation title, got ${presentation.title}"
            }
        }

        presentation.allSlides
            .groupBy({ it.id.id }) { it }
            .filterValues { it.size > 1 }
            .mapValues { (_, list) -> list.map { it.toString() } }
            .forEach { (id, list) ->
                Notifier.error {
                    "Duplicate id `$id` for ${list.joinToString()}"
                }
            }

        return presentation
    }


}