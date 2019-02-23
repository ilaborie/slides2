package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.Config
import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.SlideEngine.notifier
import io.github.ilaborie.slides2.kt.cli.Notifier
import io.github.ilaborie.slides2.kt.engine.contents.h1
import io.github.ilaborie.slides2.kt.engine.contents.h2
import io.github.ilaborie.slides2.kt.engine.contents.h3
import io.github.ilaborie.slides2.kt.engine.contents.p
import io.github.ilaborie.slides2.kt.engine.plugins.CheckContentPlugin
import io.github.ilaborie.slides2.kt.engine.renderers.*
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.jvm.JvmStopWatch
import java.io.File


fun main() {

    val config = Config(
        notifier = Notifier(JvmStopWatch),
        output = JvmFolder(File("src/main/web"), notifier = notifier)
    )

    SlideEngine
        .apply { notifier = config.notifier }
        // Plugins
        .registerContentPlugin(CheckContentPlugin(config.notifier))
        // Base
        .registerRenderers(TextTextRenderer, TextHtmlRenderer)
        .registerRenderers(TitleTextRenderer, TitleHtmlRenderer)
        .registerRenderers(ParagraphTextRenderer, ParagraphHtmlRenderer)
        .registerRenderers(StyledTextTextRenderer, StyledTextHtmlRenderer)
        // Presentation, Part, Slide
        .registerRenderer(PresentationHtmlRenderer)
        .registerRenderer(PartHtmlRenderer)
        .registerRenderer(SlideHtmlRenderer)

    val presentation = Presentation(title = "Test".h1) + (
            Part(title = "A part".h2) + (
                    Slide(title = "A slide".h3) +
                            "lorem ipsum".p
                    )
            )

    with(SlideEngine) {
        presentation.render(config)
    }

}