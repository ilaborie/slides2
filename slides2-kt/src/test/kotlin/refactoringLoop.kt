import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Info
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Warning
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
        .use(MathJaxPlugin())
        .run(config, refactoringLoop, listOf(Theme.jugTls))

}

val refactoringLoop = pres(id = "refactoringLoop", extraStyle = "style", title = { refactoringLoopTitle() }) {
    part("Introduction", skipHeader = true) {
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
        slide("Parcours - Java") {
            sourceCode("code/recursion/transform.java")
        }
        slide("Parcours - Kotlin") {
            sourceCode("code/recursion/transform.kt")
            sourceCode("code/recursion/transform2.kt")
        }
        slide("Parcours - Scala") {
            sourceCode("code/recursion/transform.scala")
            sourceCode("code/recursion/transform2.scala")
        }
        slide("Filtre & Sortie rapide - Java") {
            sourceCode("code/recursion/find.java")
        }
        slide("Filtre & Sortie rapide - Kotlin") {
            sourceCode("code/recursion/find.kt")
        }
        slide("Filtre & Sortie rapide - Scala") {
            sourceCode("code/recursion/find.scala")
        }
        slide("Récursion terminale") {
            asciiMath { "x! = x xx (x-1) xx ... xx 2 xx 1" }
            ul(steps = true) {
                asciiMath { "fact(x) = x xx fact(x-1)" }
                stack("`x`")
                asciiMath { "= x xx (x-1) xx fact(x-2)" }
                stack("`x - 1`", "`x`")
                asciiMath { "= x xx (x-1) xx (x-2) xx ..." }
                stack("...", "`x - 1`", "`x`")
                asciiMath { "= x xx (x-1) xx (x-2) xx ... xx 2 xx 1" }
                stack("`1`", "`2`", "...", "`x - 1`", "`x`")
            }
        }
        slide("Récursion terminale - Java") {
            h4("Game Over")
            span("Insert Kotlin or Scala<br>To continue")
        }
        slide("Récursion terminale - Kotlin") {
            sourceCode("code/recursion/tailrec.kt")
        }
        slide("Récursion terminale - Scala") {
            sourceCode("code/recursion/tailrec.scala")
        }
        slide("Principe  récursion terminale") {
            code("javascript") {
                """tailRecFunc(scope, state) =
                  |  if (isFinish(scope)) computeResult(state)
                  |  else
                  |    (head, subScope) := scope
                  |    newState := reduce(state, head)
                  |    tailRecFunc(subScope, newState)
                """.trimMargin()
            }
        }
        slide("Bilan récusion") {
            ul {
                html { "🧩 découpage en petites tâches" }
                html { "🤯 lisibilité" }
                html { "✋ contrôle de l'arrêt" }
                html { "📚 ATTENTION aux <code>StackOverflowError</code>" }
            }
        }
    }
    part(partTitle = { markdown { "## `Stream`" } }, id = "stream") {
        slide("Création") {
            todo {
                "From List"
                "From Array"
                "of, empty"
                "Stream Support"
            }
        }
        slide("Opération paresseuses") {
            // https://typealias.com/guides/kotlin-sequences-illustrated-guide/
        }
        slide("Opérations finales") {
            // ...
        }
        slide("map") {
            // == transform
        }
        slide("filter") {
            // == filter
        }
        slide("fold / reduce") {
            // == accumulation
        }
        slide("foldLeft vs foldRight") {}
        slide("flatMap") {
            // == imbrication
        }
        slide("Collectors") {
            // == accumulation généraliste
        }
        slide("Zip et ZipWithIndex") {
            //
        }
        slide("Bilan Stream - Java") {
            // Horreur API
        }
        slide("Bilan Stream - Kotlin & Scala") {
            //
        }
    }
    part("Qui est le meilleur") {
        slide("Norme") {}
        slide("Exemples") {}
        slide("MonteCarlo π") {}
        slide("Perforamce") {}
        slide("Élégance du code") {}
        slide("Mon avis") {
            ol(steps = true) {
                html { "Privilégier la clarté du code" }
                html { "Privilégier la maintenabilité du code" }
                html { "Utilisez les bonnes structures de données" }
            }
            notice(Warning, "Attention", classes = setOf("step")) {
                html { "🙏 Faites-vous votre propre avis" }
            }
        }
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

private fun ContainerBuilder.stack(vararg strings: String) {
    ul(classes = setOf("stack")) {
        strings.forEach { span(it) }
    }
}