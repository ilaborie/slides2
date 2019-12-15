import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.jvmConfig

private const val id = "javaHistory"

fun main() {
    val config = jvmConfig(from = "presentations/javaHistory", notes = "notes.md")

    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin)
        .use(PrismJsPlugin(showLines = true, languages = listOf("java", "scala", "kotlin")))
        .use(MathJaxPlugin())
        .run(config, javaHistory, listOf(Theme.jugTls))


    val outputDir = config.output / id
    // Copy video
//    val videoDir = config.input / "video"
//    listOf("giphy.mp4").forEach {
//        videoDir.copyOrUpdate(it, outputDir)
//    }
    // Copy script
//    val scriptDir = config.input / "script"
//    listOf("cssClockwork.js").forEach {
//        scriptDir.copyOrUpdate(it, outputDir)
//    }
    // Copy img
    val imgDir = config.input / "logos"
    listOf("toulouse-skyline.png").forEach {
        imgDir.copyOrUpdate(it, outputDir)
    }
}

private val javaHistory = pres(
    id = id,
    title = { h1 { html { "Qui suis-je ? d’où viens-je ? où vais-je ?<br>Métaphysique de <strong>Java</strong>" } } },
    extraStyle = "style"
) {
    part("Introduction", skipHeader = true) {
        //        slide("Speakers", setOf("header-hidden", "steps")) {
//            speaker(
//                fullName = "Igor Laborie",
//                classes = setOf("monkeyPatch"),
//                src = "speakers/igor.jpg",
//                info = "Expert Web & Java",
//                links = mapOf(
//                    "@ilaborie" to "https://twitter.com/ilaborie",
//                    "igor@monkeypatch.io" to "mailto:igor@monkeypatch.io"
//                )
//            )
//            figure("logos/monkeypatch.svg", "MonkeyPatch")
//            html { "<h4 class=\"designer step\">I'm not a designer <span class=\"blink step\">!</span></h4>" }
//        }
        slide("Disclaimer", setOf("header-hidden", "steps")) {
            // TODO reprendre le dislaimer
            markdown {
                """
                
            """.trimIndent()
            }
        }
    }

    part("Il était une fois") {
        //        - Naissance de Java
//        - Context historique,
//        - Green Team travail sur [Star 7](https://www.youtube.com/watch?v=1CsTH9S79qI) en 1992
//        - names: Greentalk, Oak, Java
//        - Java 1.0: Jan. 1996 (version de HotJava dispo publiquement en 1995)
//        - Usages
//        - VS C, C++
//        - VM: `Write once, Run everywhere`
//        - ObjectOriented
//        - perf ... 1 4 10
//        - Internet & Applet
//        - hiatus avec Microsoft => C#
//        - Java 1.2
//        - GC
//        - Server JEE
//                - Java 5: Generics, enum
//        - Oracle 2010
//        - distribué clould
//                - frontend
//        - IOT
//        - Java 7: invokeDynamic
//        - Java 8: Lambda
//
//        ### Status Actuel
//
//        Perf now: <https://thenewstack.io/which-programming-languages-use-the-least-electricity/>
//        <https://www.techempower.com/benchmarks/>
//
//
//        Backward compatibility
//
//                Release 6mois, OpenSource, mais $
//
//        Vs Alternative lang
//
//        JakartaEE
//
//        GraalVM
//
//        ### Future
//
//        GC faible latence
//
//        * Amber: records (14 ou 15), sealed types, pattern matching
//        * Valhalla: value type (memory performance)
//        * Loom: light thread, TCO ?
//        * Panama: better interop with native
//
//        [Brian Goetz: "Java -- A Look Ahead" | Java At Google Summit 2019](https://www.youtube.com/watch?v=GAa54jXKbn4)
//
//        **Compatibility**
//
//
//        ### Perspective
//
//        Troll: Is Java a new COBOL?
//
//
//        Question: quel seront les usages
//
//        UI, JEE ?
//        Server micro?
//
//        GraalVM
//
//        Marc Reinhold question DevoxxBE
//
//                Vs Python, Vs Haskell, Vs JavaScript
//        Vs Go, Rust
//
//        WASM ?
//
//        ### Conclusion
//
//        Communauté
//
//        => Rester curieux, regarder autre chose,
//        continuous learning

    }

    part("Conclusion") {
        // https://youtu.be/OKIKEccotUg?t=3142
        // https://youtu.be/OKIKEccotUg?t=3157
        slide("Mais où va-t-on ?", styles = setOf("header-hidden", "steps")) {
            ul {
                quote {
                    html { "Je prédit:<br> Toutes les prédicateurs auront tort" }
                }
                html { "🔮 dur à prédir, mais ..." }
                html { "tendances: code + distribé, platformes + petites, + de chose fait <em>compile time</em>" }
            }
        }

        slide("Devenir expérimenté•e") {
            ul {
                html { "Avoir un aperçu global des changement" }
                html { "Suivre les évolutions qui nous intéresses" }
                html { "`=>` Continuous learning" }
            }
        }

        slide("Merci") {
            ul {
                h4("Questions ?")
            }
            qrCode("https://ilaborie.github.io/slides2/javaHistory/index-jug-tls.html")
        }
    }
}
