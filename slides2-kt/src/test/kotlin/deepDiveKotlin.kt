import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.markdown
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.rivieraDev19
import io.github.ilaborie.slides2.kt.engine.contents.BarChart.Companion.BarChartCustom
import io.github.ilaborie.slides2.kt.engine.contents.BarChart.Companion.BarChartSmallerBetter
import io.github.ilaborie.slides2.kt.engine.contents.barChart
import io.github.ilaborie.slides2.kt.engine.contents.inlineFigure
import io.github.ilaborie.slides2.kt.engine.contents.speaker
import io.github.ilaborie.slides2.kt.engine.contents.table
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse.Companion.caniuse
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet.Companion.tweet
import io.github.ilaborie.slides2.kt.jvm.jvmConfig

private const val id = "deepDiveKotlin"

fun main() {
    val config = jvmConfig("presentations/deepDiveKotlin")

    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin, GridPlugin)
        .use(PrismJsPlugin(showLines = true, languages = listOf("typescript")))
        .run(config, deepDiveKotlin, listOf(rivieraDev19))

//    val devoxxImgs = config.input / "img" / "devoxx"
//    val outputDir = config.output / id
//    listOf("background_v1.png", "background_v2.png", "background_white_v1.png", "footer_v2.png").forEach {
//        devoxxImgs.copyOrUpdate(it, outputDir)
//    }
//
//    // Copy images
//    (config.input / "img").copyOrUpdate("doitdoit.gif", config.output / id)
}

val deepDiveKotlin = pres(id = id, extraStyle = "style", title = "dee") {
    part("Introduction", skipHeader = true) {
        slide("Speakers", setOf("header-hidden")) {
            speaker(
                fullName = "Igor Laborie",
                classes = setOf("monkeyPatch"),
                src = "speakers/igor.jpg",
                info = "Expert Web & Java",
                links = mapOf(
                    "@ilaborie" to "https://twitter.com/ilaborie",
                    "igor@monkeypatch.io" to "mailto:igor@monkeypatch.io"
                )
            )
            figure("logos/toptal.svg", "Toptal")
            figure("logos/monkeypatch.svg", "MonkeyPatch")
        }
    }
}
//
//private fun ContainerBuilder.litElementTitle() {
//    h2("Lit-Elements")
//    figure("logos/lit-element.svg", "LitElement")
//    link("https://lit-element.polymer-project.org/")
//}
//
//private fun ContainerBuilder.stencilTitle() {
//    h2("StencilJS")
//    inlineFigure("logos/stencil.svg", "StencilJS")
//    link("https://stenciljs.com/")
//}
//
//private fun ContainerBuilder.webComponentTitle() {
//    h2("Web Components")
//    inlineFigure("logos/web-components.svg", "Web Components")
//}
//
//private fun ContainerBuilder.mainTitle() {
//    h1("Web Components")
//    ul(classes = setOf("list-inline")) {
//        listOf(
//            inlineFigure("logos/web-components.svg", "Natif"),
//            inlineFigure("logos/stencil.svg", "StencilJS"),
//            figure("logos/lit-element.svg", "LitElement")
//        )
//    }
//}