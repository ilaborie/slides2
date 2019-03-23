import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Danger
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Info
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Tips
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

    (config.input / "img").copyOrUpdate("sloth.jpg", config.output / id)

}

private const val id = "refactoringLoop"
val refactoringLoop = pres(id = id, extraStyle = "style", title = { refactoringLoopTitle() }) {
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
        slide("Transformation", setOf("steps-replace")) {
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
            sourceCode("code/recursion/transform.java") // FIXME why no linenumbers
        }
        slide("Parcours - Kotlin", setOf("steps-replace")) {
            ul(steps = true) {
                sourceCode("code/recursion/transform.kt")
                sourceCode("code/recursion/transform2.kt")
            }
        }
        slide("Parcours - Scala", setOf("steps-replace")) {
            ul(steps = true) {
                sourceCode("code/recursion/transform.scala")
                sourceCode("code/recursion/transform2.scala")
            }
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
        slide("Récursion non terminale") {
            asciiMath { "x! = x xx (x-1) xx ... xx 2 xx 1" }
            asciiMath { "1! = 0!  = 1" }
            ul(steps = true) {
                asciiMath { "fact(x)" }
                stack()
                asciiMath { "x xx fact(x-1)" }
                stack("`x`")
                asciiMath { "x xx (x-1) xx fact(x-2)" }
                stack("`x - 1`", "`x`")
                asciiMath { "x xx (x-1) xx (x-2) xx ..." }
                stack("...", "`x - 1`", "`x`")
                asciiMath { "x xx (x-1) xx (x-2) xx ... xx 2 xx 1" }
                stack("`1`", "`2`", "...", "`x - 1`", "`x`")
            }
        }
        slide("Récursion terminale") {
            ul(steps = true) {
                asciiMath { "fact(x) = fact(x, 1)" }
                asciiMath { "fact(x-1, x xx 1)" }
                asciiMath { "fact(x-2, x xx (x-1))" }
                asciiMath { "fact(... , x xx (x-1) xx (x-2) xx ..)" }
                asciiMath { "fact(1, x xx (x-1) xx (x-2) xx ... xx 2)" }
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
        slide("Création 1/2") {
            sourceCode("code/stream/create1.java")
        }
        slide("Création 2/2") {
            sourceCode("code/stream/create2.java")
        }
        slide("map") {
            sourceCode("code/stream/map.java")
        }
        slide("filter") {
            sourceCode("code/stream/filter.java")
        }
        slide("Stream is Lazy", setOf("header-hidden")) {
            sourceCode("code/stream/lazy.java")
            ul(steps = true) {
                pre("lorem,...,amet,LOREM,...,AMET,AMET\nStream created")
                pre("Stream created")
            }
        }
        slide("Sloth", setOf("header-hidden")) { }
        slide("Opérations paresseuses & terminales") {
            definitions {
                term("Opérations paresseuses") {
                    html { "<code>map</code>" }
                    html { "<code>filter</code>" }
                    html { "<code>flatMap</code>" }
                    html { "<code>peek</code>" }
                    html { "<code>distinct</code>" }
                    html { "<code>limit</code> et <code>skip</code>" }
                    html { "..." }
                }
                term("Opérations terminales") {
                    html { "<code>reduce</code> et <code>collect</code>" }
                    html { "<code>findAny</code> et <code>findFirst</code>" }
                    html { "<code>forEach</code>" }
                    html { "<code>count</code>" }
                    html { "..." }
                }
            }
        }
        slide("Parallèle") {
            markdown { "Les `Stream` peuvent être exécuté en parallèle, via le `ForkJoinPool`" }
            notice(Tips) {
                markdown { "On peut utiliser `Stream#sequential()` ou `Stream#parallel()` pour basculer vers une exécution séquentiel, ou parallèle." }
            }
        }
        slide("flatMap 1/2") {
            sourceCode("code/stream/flatmap.java")
        }
        slide("flatMap 2/2", setOf("header-hidden")) {
            ul(steps = true) {
                (0..6).forEach {
                    file("anim/flatmap-$it.html")
                }
            }
        }
        slide("Reduce 1/2") {
            sourceCode("code/stream/reduce1.java")
            notice(Info) {
                markdown { "On appel souvent cette méthode `foldLeft`" }
            }
        }
        slide("Reduce 2/2") {
            notice(Tips, title = "Cas particulier") {
                markdown { "Si `Element == Accumulator`, on peut utiliser un `reduce`" }
                markdown { "Les `count`, `min`, `max`, `sum`, ... sont des réductions particulières" }
            }
            sourceCode("code/stream/reduce2.java")
        }
        slide("collect & Collectors") {
            markdown { "Les `Stream#collect` sont juste une généalisation du `reduce`" }
            sourceCode("code/stream/collect.java")
        }
        slide("Collectors classiques") {
            markdown { "Dans `java.util.stream.Collectors`" }
            ul {
                markdown { "`toXXX` pour constuire une collection XXX" }
                markdown { "`toMap` pour constuire un `Map`" }
                markdown { "`groupBy` pour grouper en une `Map<K,List<V>>`" }
                markdown { "`joining` pour constuire une `String`" }
                markdown { "`summarizingXXX` pour les statistiques sur un nombre XXX" }
                html { "..." }
            }
        }
        slide("Illustration Java", setOf("header-hidden")) {
            sourceCode("code/stream/exemple.java")
        }
        slide("Et l'index ?") {
            markdown { "Et si j'ai besoin de l'_index_ ?" }
            ul {
                markdown { "😢 pas faisable facilement et _proprement_ en Java" }
            }
        }
        slide("Nouveauté Java 9+") {
            definitions {
                term("JDK 9") {
                    html { "<code>Stream#takeWhile</code> et <code>Stream#dropWhile</code>" }
                    html { "<code>Stream#iterate</code> avec un predicat" }
                    html { "<code>Stream#ofNullable</code>" }
                    html { "<code>Option#stream</code>" }
                    html { "<code>Collectors#flatMapping</code> et <code>Collectors#filtering</code>" }
                    html { "<code>String#chars</code> et <code>String#codePoints</code>" }
                }
                term("Java 10") {
                    html { "<code>Collectors#toUnmodifiableXXX</code> pour les <code>List</code>, <code>Set</code> et <code>Map</code>" }
                }
                term("Java 11") {
                    html { "<code>String#lines</code>" }
                }
                term("Java 12") {
                    html { "<code>Collectors#teeing</code>" }
                }
            }
        }
//        slide("Trucs et astuces") {
//            // firstNotNull
//            // read file
//        }
        slide("Bilan Stream") {
            h4("🤗")
            notice(Danger, title = "A proscire") {
                markdown { "les effets de bord ! (on tolère les _log_ dans le `peek`)" }
                markdown { "les opérations non-associatives dans le code paralèlle" }
                markdown { "les streams sur des `Interger`, `Double`, `Long`" }
            }
            notice(Warning) {
                markdown { "sans bonne raison, ne faites pas de `Stream` parallèles" }
                markdown { "la lisibilité est important" }
            }
        }
        slide("Bilan Stream - Java") {
            h4("😡")
            ul(steps = true) {
                markdown { "`T  reduce(T identity, BinaryOperator<T> accumulator)` et <br>`Optional<T> reduce(BinaryOperator<T> accumulator)`" }
                markdown { "`IntStream`, `DoubleStream`, `LongStream`, avec `mapToObj`, `mapToXXX`, ..." }
                markdown { "Le _boilerplate_, par exemple `groupBy`" }
//                markdown { "💉 [∨∧∨r](http://www.vavr.io)" }
            }
        }
        slide("Kotlin 1/3") {
            sourceCode("code/stream/exemple1.kt")
        }
        slide("Kotlin 2/3") {
            sourceCode("code/stream/exemple2.kt")
        }
        slide("Kotlin 3/3") {
            h4("😍")
            ul(steps = true) {
                markdown { "API _lazy_ ou non" }
                markdown { "API **immutable** ou mutable" }
                markdown { "🎭 utilise juste les classes de Java" }
            }
        }
        slide("Scala 1/4") {
            sourceCode("code/stream/exemple1.scala")
        }
        slide("Scala 2/4") {
            sourceCode("code/stream/exemple2.scala")
        }
        slide("Scala 3/4") {
            h4("😻")
            ul(steps = true) {
                markdown { "API _lazy_ ou non" }
                markdown { "API **immutable** ou mutable" }
                markdown { "Pas de réutilisation de Java" }
                markdown { "API de [`Stream`](https://www.scala-lang.org/api/2.12.3/scala/collection/immutable/Stream.html) avec la possibilité de construction recusive" }

                notice(Info) {
                    markdown { "De gros changement arrivent dans la [2.13](https://www.scala-lang.org/blog/2018/06/13/scala-213-collections.html)" }
                }
            }
        }
        slide("Scala 4/4") {
            h4("🤢")
            sourceCode("code/stream/exemple-for.scala")
            markdown { "le `for` de Scala est du sucre syntaxique qui produit des `map`, `filter`, `flatMap`" }
            markdown { "du coup on peut l'utiliser sur d'autre objects qui ont `map`, `filter`, `flatMap`" }
        }
//        slide("foldLeft vs foldRight") {}
    }
    part("Qui est le meilleur") {
        slide("Norme") {}
        slide("MonteCarlo π") {
            // Explain
            // code java 1,2,3
            // code kotlin 1,2,3
            // code scala 1,2,3
        }
        slide("Perforamce") {
            // logarithm axis ... Vs
        }
        slide("Élégance du code") {
            // cf mario fusco SoC
        }
        slide("Exemples classique") {
            // ...
        }
        slide("Exemples Excel colonne") {
            // ...
        }
        slide("Mon avis 1") {
            ol(steps = true) {
                html { "Privilégier la clarté du code" }
                html { "Privilégier la maintenabilité du code" }
                html { "Utilisez les bonnes structures de données" }
            }
            notice(Warning, classes = setOf("step")) {
                html { "🙏 Faites-vous votre propre avis" }
            }
        }
        slide("Mon avis lisibilité") {
            // Règle sur la lisibilités des streams
        }
    }
    part("Conclusion") {
        slide("Bilan") {
            // lien flatmapThatShit
        }
        slide("FP") {
            // Hidden
        }
        slide("Crafters") {
            // Code review
        }
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