import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.inlineFigure
import io.github.ilaborie.slides2.kt.engine.contents.speaker
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet
import io.github.ilaborie.slides2.kt.jvm.jvmConfig


fun main() {
    val config = jvmConfig("presentations/refactoringLoop")

    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin, GridPlugin)
        .use(Tweet.Companion.TweetPlugin, CanIUse.Companion.CanIUsePlugin)
        .use(PrismJsPlugin(showLines = true, languages = listOf("java", "scala", "kotlin")))
        .run(config, refactoringLoop, listOf(Theme.jugTls))

}

val refactoringLoop = pres(id = "refactoringLoop", extraStyle = "style", title = { refactoringLoopTitle() }) {
    part("Introduction", skipHeader = true) {
        slide("Speaker", setOf("header-hidden")) {
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
            inlineFigure("logos/monkeypatch.svg", "MonkeyPatch")
        }
        slide("Back to Basics", styles = setOf("header-hidden")) {
            strong("#backToBasics")
            quote("Les frameworks naissent et meurent, les bases restent")
        }
        slide("Quizz 1", styles = setOf("header-hidden")) {
            h4("En quel langague est écrit ce code ?")
            ul(steps = true) {
                sourceCode("code/quizz1.java")
                p("Java, JavaScript, C++, C, ?")
            }
        }
        slide("Quizz 2", styles = setOf("header-hidden", "two-columns")) {
            markdown { "#### Existe-il des langagues de programmation sans `for` ?" }
            ul(steps = true) {
                html { "Haskell" }
                html { "Scala <sup>*</sup>" }
                html { "Erlang" }
                html { "Clojure" }
                html { "Assembleur" }
                html { "ByteCode Java" }
                html { "..." }
            }
        }
        roadmap("Plan")
    }
    part("Anatomie d'une boucle") {
        slide("Transformation") {
            ul(steps = true) {
                sourceCode("code/loop/transformation1.java")
                sourceCode("code/loop/transformation2.java")
                sourceCode("code/loop/transformation3.java")
                sourceCode("code/loop/transformation4.java")
            }
        }
        slide("Filtre") {
            sourceCode("code/loop/filter.java")
        }
        slide("Accumulation") {
            sourceCode("code/loop/accumulate.java")
        }
        slide("Imbrication") {
            sourceCode("code/loop/nest.java")
        }
        slide("Et le reste") {
            sourceCode("code/loop/other.java")
        }
    }
    part("Récursion") {
        slide("Parcours") {
            ul(steps = true) {
                sourceCode("code/recursion/transform.java")
                sourceCode("code/recursion/transform.kt")
                sourceCode("code/recursion/transform2.kt")
                sourceCode("code/recursion/transform.scala")
                sourceCode("code/recursion/transform2.scala")
            }
        }
        slide("Sortie rapide") {}
        slide("Récursion terminale") {}
        slide("Bilan récursion - Java") {}
        slide("Bilan Kotlin &  Scala") {}
    }
    part(partTitle = { markdown{"## `Stream`"} },id = "stream") {
        slide("Création") {}
        slide("Opération paresseuses") {}
        slide("Opérations finales") {}
        slide("map") {}
        slide("filter") {}
        slide("fold / reduce") {}
        slide("flatMap") {}
        slide("Collectors") {}
        slide("Zip et ZipWithIndex") {}
        slide("Bilan Stream - Java") {}
        slide("Bilan Stream - Kotlin & Scala") {}
    }
    part("Qui est le meilleur") {
        slide("Norme") {}
        slide("Exemples") {}
        slide("MonteCarlo π") {}
        slide("Perforamce") {}
        slide("Élégance du code") {}
    }
    part("Conclusion") {
        slide("Bilan") {}
        slide("FP") {}
        slide("Crafters") {}
    }
}


private fun ContainerBuilder.refactoringLoopTitle() {
    h1("🏋️‍♂️ Refactoring sans les <code>for</code>")
}