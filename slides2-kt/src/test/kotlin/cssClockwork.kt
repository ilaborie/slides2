import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind
import io.github.ilaborie.slides2.kt.engine.contents.speaker
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.jvmConfig

private const val id = "cssClockwork"

fun main() {
    val config = jvmConfig(from = "presentations/cssClockwork", notes = "notes.md")

    val cssClockworkPlugin = object : WebPlugin {
        override val name: String = "CssClockworkPlugin"
        override fun scripts(): List<Script> =
            listOf(Script.script("cssClockwork.js"))
    }

    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin)
        .use(PrismJsPlugin(), MathJaxPlugin(), cssClockworkPlugin)
        .run(config, cssClockwork, listOf(Theme.mixit19))


    val outputDir = config.output / id
    // Copy video
    val videoDir = config.input / "video"
    listOf("css-wtf.mp4").forEach {
        videoDir.copyOrUpdate(it, outputDir)
    }
    // Copy script
    val scriptDir = config.input / "script"
    listOf("cssClockwork.js").forEach {
        scriptDir.copyOrUpdate(it, outputDir)
    }
}

val cssClockwork = pres(
    id = id,
    extraStyle = "style",
    title = { h1 { html { "⏰ CSS Clockworks" } } }) {
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
            figure("logos/monkeypatch.svg", "MonkeyPatch")
        }
        slide("CSS is Awesome! 1/2") {
            ul(steps = true) {
                markdown { "> The [Rule of Least Power](https://www.w3.org/2001/tag/doc/leastPower.html) suggests choosing the least powerful language suitable for a given purpose" }
                markdown { "Bien connaitre les [sélecteurs](https://developer.mozilla.org/fr/docs/Web/CSS/S%C3%A9lecteurs_CSS), et les [unités](https://developer.mozilla.org/fr/docs/Web/CSS/length)" }

                markdown { "Evitez trop d'adhérences aux pré-processeur" }
                markdown { "[Flexbox](https://developer.mozilla.org/fr/docs/Web/CSS/CSS_Flexible_Box_Layout) et [CSS Grid](https://developer.mozilla.org/fr/docs/Web/CSS/CSS_Grid_Layout) c'est génial !" }
                markdown { "Les [pseudo-éléments](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-elements) `::before` et `::after` sont génial !" }
                markdown { "Les [pseudo-classes](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-classes) commme `:checked` c'est puissant" }
                strong("Traitez le CSS comme du code !")
            }
        }
        slide("CSS is Awesome! 2/2") {
            ul(steps = true) {
                markdown { "SVG et du CSS pour déssiner une 🦄 " }
                markdown { "Live-coding CSS sans JS" }
                file("code/unicorn.html")
                link("https://www.youtube.com/watch?v=fPObs60585w", "CSS is Awesome au Devfest Toulouse 2017")
                link("http://www.monkeypatch.io/blog/2017/2017-05-02-makingof_css_is_awesome/", "Blog  Making Of")
            }
        }
    }

    part("Live-coding") {
        // Step 1
        slide("Le cadre", setOf("live-code")) {
            sourceCode("code/step1.css")
            demo(1)
        }
        slide("La cadre - liens") {
            ul {
                markdown { "[L'élément `<time>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/time)" }
                markdown { "[`box-sizing`](https://developer.mozilla.org/en-US/docs/Web/CSS/box-sizing)" }
                markdown { "[`border-radius`](https://developer.mozilla.org/en-US/docs/Web/CSS/border-radius)" }
                markdown { "[`box-shadow`](https://developer.mozilla.org/en-US/docs/Web/CSS/box-shadow)" }
            }
            notice(NoticeKind.Tips) {
                markdown { "`currentColor` et les `em` peuvent être utilisé comme des variables." }
            }
        }
        slide("une aiguille", setOf("live-code")) {
            sourceCode("code/step2.css")
            demo(1)
        }


        slide("Heure et minutes") {
            markdown {
                """
                pseudo-element
                custom properties
                position: relative, absolute
                transformation
                calc
            """.trimIndent()
            }
        }

        slide("trotteuse") {
            """
            Oops => on a besoin de vrai éléments
            """.trimIndent()
        }

        slide("Tick") {
            """
                Tick
            """.trimIndent()
        }
    }

    part("Support") {

        slide("Ça marche partout") {
            ul(steps = true) {
                markdown { "`border-radius`" }
                markdown { "..." }
            }
        }
        slide("Ça sur les navigateurs modernes") {
            ul(steps = true) {
                markdown { "..." }
            }
        }
    }

    part("Conclusion") {
        slide("A quoi ça sert ?") {
            ul(steps = true) {
                markdown { "à rien." }
            }
        }

        slide("Les moments 'WTF'") {
            html { """<video autoplay loop src="css-wtf.mp4" type="video/mp4"></video>""" }
        }

        slide("Pourquoi on en est là") {
            ul(steps = true) {
                markdown { "🕳 <del>Incompatibilité</del>" }
                markdown { "🚫 pas de cours CSS" }
                markdown { "⌛️ Pas le temps sur les projets" }
                markdown { "😡 technologie mésestimé" }
                markdown { "🛑 **Il faut changer celà !**" }
            }
        }

        slide("Comment apprendre") {
            ul(steps = true) {
                markdown { "🤹‍♂️ regardez des présentations" }
                markdown { "🎓 mentoring" }
                markdown { "📚 livres, par exemple [CSS Secrets](http://www.amazon.com/CSS-Secrets-Lea-Verou/dp/1449372635?tag=leaverou-20)" }
                markdown { "... les _side-projects_" }
                html { "maximiser `(wow)/(wtf)`" }
            }
        }

        slide("Merci") {
            h4("Questions ?")
            em { html { "(les retours sont bienvenus)" } }
            html {
                """<div class="clock unicorn"></div>"""
            }
        }
    }
}

fun ContainerBuilder.demo(step: Int, inner: String = "") {
    html { """<div class="demo">
             |  <div class="clock-step$step">$inner</div>
             |</div>""".trimMargin() }
}