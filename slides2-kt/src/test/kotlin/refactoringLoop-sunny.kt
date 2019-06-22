import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.script
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.BarChart
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Danger
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Info
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Tips
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Warning
import io.github.ilaborie.slides2.kt.engine.contents.barChart
import io.github.ilaborie.slides2.kt.engine.contents.inlineFigure
import io.github.ilaborie.slides2.kt.engine.contents.speaker
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet.Companion.TweetPlugin
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet.Companion.tweet
import io.github.ilaborie.slides2.kt.jvm.jvmConfig

private const val id = "refactoringLoop"

fun main() {
    val config = jvmConfig("presentations/refactoringLoop")

    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin)
        .use(TweetPlugin)
        .use(PrismJsPlugin(showLines = false, languages = listOf("java", "scala", "kotlin")))
        .use(MathJaxPlugin())
        .use(monteCarloPlugin)
        .run(config, refactoringLoop, listOf(Theme.sunnyTech19))

    // Copy images
    (config.input / "img").copyOrUpdate("sloth.jpg", config.output / id)
}

private val monteCarloPlugin = object : WebPlugin {
    override val name: String = "MonteCarlo Plugin"

    override fun scripts(): List<Script> =
        listOf(script("montecarlo.js"))
}

private val refactoringLoop = pres(id = id, extraStyle = "style", title = { refactoringLoopTitle() }) {
    part("Introduction", skipHeader = true) {
        slide("Back to Basics", styles = setOf("header-hidden")) {
            strong("#backToBasics")
            quote("Les frameworks et bibliothèques naissent et meurent, les bases restent.")
        }
        roadmap("Plan")
    }
    part("🔬 Anatomie d'une boucle") {
        slide("Transformation") {
            sourceCode("code/loop/transformation2.java")
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
    part("🔃 Récursion") {
        slide("Parcours - Java") {
            sourceCode("code/recursion/transform.java")
        }
        slide("Parcours - Kotlin & Scala") {
            ul(steps = true) {
                sourceCode("code/recursion/transform2.kt")
                sourceCode("code/recursion/transform2.scala")
            }
        }
        slide("Filtre & Sortie rapide - Java") {
            sourceCode("code/recursion/find.java")
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
                asciiMath { "fact(... , x xx (x-1) xx (x-2) xx ...)" }
                asciiMath { "fact(1, x xx (x-1) xx (x-2) xx ... xx 2)" }
                markdown { "⚠️ Nécessite une optimisation par le compilateur" }
                link("https://www.youtube.com/watch?v=-PX0BV9hGZY", "!!Con 2019- Tail Call Optimization: The Musical!! by Anjana Vakil & Natalia Margolis")
//                youtube("-PX0BV9hGZY")
            }
        }
        slide("Principe récursion terminale") {
            ul(steps = true) {
                code("javascript") {
                    """tailRecFunc(scope, state) =
                  |  if (isFinish(scope)) computeResult(state)
                  |  else
                  |    (head, subScope) := scope
                  |    newState := reduce(state, head)
                  |    tailRecFunc(subScope, newState)
                """.trimMargin()
                }

                sourceCode("code/loop/accumulate.java")
            }

            notes = """Lien accumulateur"""
        }
        slide("Récursion terminale - Java") {
            h4("Game Over")
            span("Insert Kotlin or Scala<br>To continue")
        }
        slide("Récursion terminale - Kotlin & Scala") {
            ul(steps = true) {
                sourceCode("code/recursion/tailrec.kt")
                sourceCode("code/recursion/tailrec.scala")
            }
        }
    }
    part(partTitle = { markdown { "## 🌊 `Stream`" } }, id = "stream") {
        slide("filter, map, & flatMap") {
            ul(steps = true) {
                sourceCode("code/stream/ex-for.java")
                sourceCode("code/stream/ex-stream.java")
            }
        }

        slide("Imbrication - flatMap 2/2", setOf("header-hidden")) {
            ul(steps = true) {
                (0..6).forEach {
                    file("anim/flatmap-$it.html")
                }
            }
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
        slide("Accumulation - Reduce") {
            sourceCode("code/stream/reduce1.java")
            notice(Tips, title = "Cas particulier") {
                markdown { "Si `Element == Accumulator`, le `accumulate` peut servir de `merge`" }
                markdown { "Les `count`, `min`, `max`, `sum`, ... sont des réductions particulières" }
            }
        }
        slide("Accumulation - collect & Collectors") {
            sourceCode("code/stream/collect.java")

            notice(Tips, title = "java.util.stream.Collectors") {
                markdown {
                    """`toSet`, `toList`, `toMap`, `groupBy`, `joining`,
                      |`summarizingInt`, `summarizingDouble`, `summarizingLong`,
                      |...""".trimMargin()
                }
            }

            notes = """Les `Stream#collect` sont justes une généralisation du `reduce`"""
        }
        slide("Et si j'ai besoin de l'index ?") {
            ul(steps = true) {
                markdown { "😢 pas faisable facilement et _proprement_ en Java" }
                markdown { "✌️ mais il y a Kotlin et Scala..." }
            }
        }
        slide("Nouveautés Java 9+") {
            definitions {
                term("Java 9") {
                    html { "<code>Stream#takeWhile</code> et <code>Stream#dropWhile</code>" }
                    html { "<code>Stream#iterate</code> avec un prédicat" }
                    html { "<code>Stream#ofNullable</code>" }
                    html { "<code>Optional#stream</code>" }
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
        slide("Bilan Stream") {
            h4("🤗")
            ul(steps = true) {
                notice(Danger, title = "À proscrire") {
                    markdown { "Les effets de bord ! (on tolère les _logs_ dans le `peek`)" }
                    markdown { "Les opérations non associatives dans des `Stream` parallèles" }
                    markdown { "Les streams sur des `Integer`, `Double`, `Long`" }
                }
                notice(Warning) {
                    markdown { "Sans bonne raison, ne faites pas de `Stream` parallèle" }
                    markdown { "La lisibilité est importante" }
                }
                notice(Tips) {
                    markdown { "On peut utiliser intelligemment les aspects paresseux" }
                }
            }
        }
        slide("Bilan Stream - Java") {
            h4("😡")
            ul(steps = true) {
                markdown { "`T  reduce(T identity, BinaryOperator<T> accumulator)` et <br>`Optional<T> reduce(BinaryOperator<T> accumulator)`" }
                markdown { "`IntStream`, `DoubleStream`, `LongStream`, avec `mapToObj`, `mapToXXX`, ..." }
                markdown { "Le _boilerplate_, par exemple `.collect(Collectors.toList())`, `Collectors.groupBy`, ..." }
                markdown { "Pas de tuples en Java" }
                markdown { "Des 🐛 dans le _lazy_ du `flatMap`, voir [Java Stream API was Broken Before JDK10](https://4comprehension.com/java-stream-api-was-broken-before-jdk10/)" }
            }
        }
        slide("Bilan Stream - Kotlin") {
            h4("😍")
            ul(steps = true) {
                markdown { "API _lazy_ avec les `Sequence` ou non directement sur les collections" }
                markdown { "API collection **immutable** ou mutable" }
                markdown { "🎭 utilise juste les classes de Java" }
            }
        }
        slide("Bilan Stream - Scala") {
            h4("😻")
            ul(steps = true) {
                markdown { "API _lazy_ avec les `Stream`/`LazyList` ou non directement sur les collections" }
                markdown { "API collection **immutable** ou mutable" }
                markdown { "Pas de réutilisation de Java" }
                markdown { "API de [`Stream`](https://www.scala-lang.org/api/2.12.8/scala/collection/immutable/Stream.html) et [LazyList](https://www.scala-lang.org/api/2.13.x/scala/collection/immutable/LazyList.html) avec la possibilité de construction récursive" }

                notice(Info) {
                    markdown { "De gros changements dans la [2.13](https://www.scala-lang.org/blog/2018/06/13/scala-213-collections.html)" }
                }
                notice(Tips) {
                    markdown { "Le `for` de Scala est du sucre syntaxique qui produit des `map`, `filter`, `flatMap`" }
                    markdown { "Du coup on peut l'utiliser sur d'autre objects qui ont `map`, `filter`, `flatMap`" }
                }
            }
        }
    }
    part("🏆 Qui est le meilleur ?") {
        slide("Relation d'ordre") {
            markdown {
                """Si on veut déterminer le meilleur, ils nous faut une relation d'ordre, laquelle ?"""
            }
            ul(steps = true) {
                markdown { "🏎 le plus rapide ?" }
                markdown { "💾 le moins couteux en mémoire ?" }
                markdown { "🧱 le plus maintenable ?" }
                markdown { "🈂 le plus lisible ?" }
                markdown { "..." }
            }
        }
        slide("Java & performances") {
            ul {
                markdown { "📏 faire des micro-benchmark en Java, c'est pas évident" }
                markdown { "♨️ la JVM à besoin de chauffer (JIT)" }
                link("http://openjdk.java.net/projects/code-tools/jmh/", "JMH")
                link(
                    "https://daniel.mitterdorfer.name/articles/2014/jmh-microbenchmarking-intro/",
                    "Microbenchmarking in Java with JMH (5 articles)"
                )
            }
        }
        slide("Rust", styles = setOf("header-hidden")) {
            figure("img/rust.png", "Rewrite everything in Rust")
        }
        slide("MonteCarlo π") {
            html { """<div class="montecarlo"></div>""" }
            asciiMath { """((pi * r^2) / 4 ) / r^2 = pi  / 4 ~~ ("nb. in") / ("nb. total")""" }
            html { """<div>avec <span class="math-ascii">`r=1`</span></div>""" }
            html {
                """<div class="result">
                    <div class="pi"><span class="math-ascii">`pi ~~`</span><output></output></div>
                    <div class="info"><span class="in"></span><span class="out"></span><span class="count"></span></div>
                   </div>
                """.trimIndent()
            }
            html { """<button class="btn"></button> """ }
        }
        slide("MonteCarlo - Point") {
            sourceCode("code/montecarlo/point.java")
        }
        slide("MonteCarlo - Java for") {
            sourceCode("code/montecarlo/for.java")
        }
//        slide("MonteCarlo - Java 3") {
//            sourceCode("code/montecarlo/recursion.java")
//        }
        slide("MonteCarlo - Java stream") {
            sourceCode("code/montecarlo/stream.java")
        }
        slide("MonteCarlo - Java stream parallèle") {
            sourceCode("code/montecarlo/streamP.java")
        }
        slide("MonteCarlo - Kotlin for") {
            sourceCode("code/montecarlo/for.kt")
        }
        slide("MonteCarlo - Kotlin tailrec") {
            sourceCode("code/montecarlo/recursion.kt")
        }
        slide("MonteCarlo - Kotlin collection") {
            sourceCode("code/montecarlo/col.kt")
        }
        slide("MonteCarlo - Kotlin séquence") {
            sourceCode("code/montecarlo/stream.kt")
        }
        slide("MonteCarlo - Kotlin séquence parallèle *") {
            sourceCode("code/montecarlo/streamP.kt")
        }
        slide("MonteCarlo - Scala for") {
            sourceCode("code/montecarlo/for.scala")
        }
        slide("MonteCarlo - Scala tailrec") {
            sourceCode("code/montecarlo/recursion.scala")
        }
        slide("MonteCarlo - Scala collection") {
            sourceCode("code/montecarlo/col.scala")
        }
        slide("MonteCarlo - Scala stream") {
            sourceCode("code/montecarlo/stream.scala")
        }
        slide("MonteCarlo - Scala stream parallèle") {
            sourceCode("code/montecarlo/streamP.scala")
        }
        slide("Disclaimer", styles = setOf("header-hidden")) {
            markdown {
                """
                  |Java 11,
                  |Kotlin v1.3.31,
                  |Scala v2.13.0
                  |
                  |Sur iMac13.2, avec un Intel core i7 3.4GHz, 4 cores
                  |
                  |Et le JRE HotSpot 11.0.3 AdoptOpenJDK
                  |
                  |#### Disclaimer
                  |
                  |>REMEMBER: The numbers below are just data. To gain reusable insights,
                  |you need to follow up on why the numbers are the way they are.
                  |Use **profilers** (see `-prof`, `-lprof`), design factorial experiments,
                  |perform baseline and negative tests that provide experimental control,
                  |make sure the benchmarking environment is safe on JVM/OS/HW level,
                  |**ask for reviews** from the domain experts.<br>
                  |**Do not assume the numbers tell you what you want them to tell.**
                  |""".trimMargin()
            }
            html {
                """🎳 venez lire, tester, critiquer, proposer des PR sur le <a href="https://github.com/ilaborie/refactorLoops">dépôt Github</a>"""
            }
        }
        slide("MonteCarlo - performance 1000 points") {
            barChart(
                "1_000 points sur OpenJDK (HotSpot) 8.0.202",
                values = mapOf(
                    "<span class=\"kotlin\"></span> tailrec" to 24345, //
                    "<span class=\"kotlin\"></span> for" to 24335,
                    "<span class=\"scala\"></span> tailrec" to 24325,
                    "<span class=\"java\"></span> for" to 24241, //
                    "<span class=\"scala\"></span> for" to 23606,
                    "<span class=\"kotlin\"></span> sequence" to 20876,
                    "<span class=\"kotlin\"></span> collection" to 20850,
                    "<span class=\"java\"></span> stream" to 20046,
                    "<span class=\"scala\"></span> collection" to 19711, //
//                    "<span class=\"scala\"></span> LazyList" to 12427, //
                    "<span class=\"scala\"></span> stream" to 10953
                ),
                factor = { it },
                infoFn = { "$it ops/s" },
                mode = BarChart.Companion.BarChartCustom(
                    min = 0,
                    max = 24345,
                    low = 19000,
                    high = 22000,
                    optimum = 24345
                )
            )
            asciiMath { """"tailrec" ~~ 41.1mus > "for" ~~ 41.3mus > "collection" ~~ 48.0mus > "stream" ~~ 49.9mus""" }
        }
        slide("MonteCarlo - performance 10M points") {
            barChart(
                "10_000_000 points sur OpenJDK (HotSpot) 8.0.202",
                values = mapOf(
                    "<span class=\"kotlin\"></span> tailrec" to 2.434,
                    "<span class=\"kotlin\"></span> for" to 2.432,
                    "<span class=\"scala\"></span> tailrec" to 2.431, // 403ms
                    "<span class=\"java\"></span> for" to 2.403, // 474ms
                    "<span class=\"java\"></span> stream" to 2.361,
                    "<span class=\"scala\"></span> for" to 2.355,
                    "<span class=\"kotlin\"></span> sequence" to 2.078,
                    "<span class=\"scala\"></span> collection" to 1.835, // 601ms
                    "<span class=\"kotlin\"></span> collection" to 1.233,
                    "<span class=\"scala\"></span> stream" to 0.360
//                    "<span class=\"scala\"></span> LazyList" to 0.252
                ),
                factor = { (it * 1000).toInt() },
                infoFn = { "$it ops/s" },
                mode = BarChart.Companion.BarChartCustom(
                    min = 0,
                    max = 2434,
                    low = 1800,
                    high = 2000,
                    optimum = 2434
                )
            )
            asciiMath { """"tailrec" ~~ 411ms > "for" ~~ 416ms > "stream" ~~ 424ms > "collection" ~~ 545ms""" }
        }
        slide("MonteCarlo - performance parallèle") {
            barChart(
                "10_000_000 points sur OpenJDK (HotSpot) 8.0.202",
                values = mapOf(
                    "<span class=\"kotlin\"></span> tailrec" to 2.434,
                    "<span class=\"kotlin\"></span> for" to 2.432,
                    "<span class=\"scala\"></span> tailrec" to 2.430, // 403ms
                    "<span class=\"java\"></span> for" to 2.403, // 474ms
                    "<span class=\"java\"></span> stream" to 2.361,
                    "<span class=\"scala\"></span> for" to 2.355,
                    "<span class=\"kotlin\"></span> sequence" to 2.078,
                    "<span class=\"scala\"></span> collection" to 1.835, // 601ms
                    "<span class=\"kotlin\"></span> sequence ∥" to 1.571,
                    "<span class=\"kotlin\"></span> collection" to 1.233,
                    "<span class=\"scala\"></span> stream ∥" to 0.468,
                    "<span class=\"scala\"></span> stream" to 0.360,
//                    "<span class=\"scala\"></span> LazyList ∥" to 0.305,
//                    "<span class=\"scala\"></span> LazyList" to 0.252,
                    "<span class=\"java\"></span> stream ∥" to 0.249
                ),
                factor = { (it * 1000).toInt() },
                infoFn = { "$it ops/s" },
                mode = BarChart.Companion.BarChartCustom(
                    min = 0,
                    max = 2434,
                    low = 1500,
                    high = 2000,
                    optimum = 2482
                )
            )
            markdown { "😱 le code parallèle" }
        }
        slide("WAT !") {
            figure("img/wat.jpg", "Wat ?")
        }
        slide("MonteCarlo - performance parallèle 2") {
            barChart(
                "10_000_000 points sur OpenJDK (HotSpot) 8.0.202",
                values = mapOf(
                    "<span class=\"java\"></span> stream ∥ 2" to 8.132,
                    "<span class=\"kotlin\"></span> sequence ∥ 2" to 6.171,
                    "<span class=\"kotlin\"></span> tailrec" to 2.434,
                    "<span class=\"kotlin\"></span> for" to 2.432,
                    "<span class=\"scala\"></span> tailrec" to 2.430, // 403ms
                    "<span class=\"java\"></span> for" to 2.403, // 474ms
                    "<span class=\"java\"></span> stream" to 2.361,
                    "<span class=\"scala\"></span> for" to 2.355,
                    "<span class=\"kotlin\"></span> sequence" to 2.078,
                    "<span class=\"scala\"></span> collection" to 1.835, // 601ms
                    "<span class=\"kotlin\"></span> collection" to 1.233,
                    "<span class=\"scala\"></span> stream" to 0.360,
                    "<span class=\"scala\"></span> LazyList" to 0.252
                ),
                factor = { (it * 1000).toInt() },
                infoFn = { "$it ops/s" },
                mode = BarChart.Companion.BarChartCustom(
                    min = 0,
                    max = 8132,
                    low = 5000,
                    high = 7000,
                    optimum = 8132
                )
            )
        }
        slide("Ce qui change") {
            ul(steps = true) {
                markdown {
                    "🎲 depuis Java 8 [`SplittableRandom`](https://docs.oracle.com/javase/8/docs/api/java/util/SplittableRandom.html)"
                }
                sourceCode("code/montecarlo/point2.java", data = mapOf("line" to "11,12"))
            }
        }
        slide("Separation of Concerns") {
            ul(classes = setOf("compare")) {
                file("code/soc/imp.html")
                file("code/soc/stream.html")
            }
            ul {
                markdown { "[**@mariofusco**](https://twitter.com/mariofusco/status/571999216039542784)" }
                link("https://www.youtube.com/watch?v=84MfG4tp30s", "Lazy Java par Mario Fusco")
            }
        }
        // FIXME slide
        slide("Prédisposition aux 🐛") {
            sourceCode("code/loop/transformation1.java")
        }
        slide("Prédisposition aux 🐛 dangereux") {
            sourceCode("code/loop/transformation3.java")
        }
    }
    part("Conclusion") {
        slide("Bilan") {
            span("<code>goto</code> firstQuote")
            quote {
                markdown {
                    """Les frameworks et bibliothèques naissent et meurent,
                      |les bases <ins>et les styles de programmation</ins> restent.""".trimMargin()
                }
            }
            quote {
                markdown {
                    """Il suffit de choisir le langage, les frameworks,
                      |et les bibliothèques qui correspondent aux styles.<br>
                      |Choisissiez un style qui correspond à vos contraintes et vos goûts.""".trimMargin()
                }
            }
            link("http://www.flatmapthatshit.com/", "Si vous avez du mal à choisir")
        }
        // TODO https://twitter.com/spf13/status/1120720001676271616
        slide("FP") {
            ul(steps = true) {
                html { "Lambda et fonctions d'ordre supérieur" }
                html { "Pas d'effet de bord" }
                html { "Immutablilté" }
                html { """<span class="math-ascii">`=>`</span> Ceci est une présentation sur la programmation fonctionnelle""" }
            }
        }
        slide("FP 🍌") {
            ul(steps = true) {
                markdown {
                    """[Functional Programming with Bananas, Lenses, Envelopes and Barbed Wire](http://eprints.eemcs.utwente.nl/7281/01/db-utwente-40501F46.pdf) - Erik Meijer and J. Hughes and M.M. Fokkinga and Ross Paterson" - 1991
                        |
                        |> We develop a calculus for lazy functional programming based
                        |on recursion operators associated with data type definitions.
                        |For these operators we derive various algebraic laws that are useful
                        |in deriving and manipulating programs.
                        |We shall show that all example functions in
                        |Bird and Wadler's "Introduction to Functional Programming"
                        |can be expressed using these operators.
                    """.trimMargin()
                }
                tweet("384105824315928577")
            }
        }
        slide("Crafters") {
            ul(steps = true) {
                html { "❓ Quand vous codez, posez-vous des questions !" }
                html { "🔪 Aiguisez votre esprit critique !" }
                html { "🗣 Partagez vos questionnements, vos solutions, vos idées farfelues !" }
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
        slide("Merci") {
            ul {
                h4("Questions ?")
                em { html { "(les retours sont bienvenus)" } }
            }
            qrCode("https://ilaborie.github.io/slides2/refactoringLoop/index-sunnytech-19.html")
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