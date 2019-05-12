import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Theme
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Tips
import io.github.ilaborie.slides2.kt.engine.contents.NoticeKind.Warning
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
        .use(PrismJsPlugin(showLines = false, lineHighlight = true), MathJaxPlugin(), cssClockworkPlugin)
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

                markdown { "Evitez trop d'adhérences aux pré-processeurs" }
                markdown { "[Flexbox](https://developer.mozilla.org/fr/docs/Web/CSS/CSS_Flexible_Box_Layout) et [CSS Grid](https://developer.mozilla.org/fr/docs/Web/CSS/CSS_Grid_Layout) sont géniaux !" }
                markdown { "Les [pseudo-éléments](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-elements) `::before` et `::after` sont géniaux !" }
                markdown { "Les [pseudo-classes](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-classes) commme `:checked` c'est puissant" }
                strong("Traitez le CSS comme du code !")
            }
        }
        slide("CSS is Awesome! 2/2") {
            ul(steps = true) {
                markdown { "Live-coding CSS sans JS" }
                markdown { "SVG et du CSS pour déssiner une 🦄 " }
                file("code/unicorn.html")
                link("https://www.youtube.com/watch?v=fPObs60585w", "CSS is Awesome au Devfest Toulouse 2017")
                link("http://www.monkeypatch.io/blog/2017/2017-05-02-makingof_css_is_awesome/", "Blog  Making Of")
            }
        }
    }

    part("Live-coding") {
        // Step 1
        slide("Le cadre", setOf("live-code")) {
            demo(1)
        }
        slide("La cadre - liens") {
            ul {
                markdown { "[L'élément `<time>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/time) existe !" }
                markdown { "[`currentColor`](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value#currentColor_keyword)" }
                markdown { "[`box-sizing`](https://developer.mozilla.org/en-US/docs/Web/CSS/box-sizing)" }
                markdown { "[`border-radius`](https://developer.mozilla.org/en-US/docs/Web/CSS/border-radius)" }
                markdown { "[`box-shadow`](https://developer.mozilla.org/en-US/docs/Web/CSS/box-shadow)" }

                notice(Tips) {
                    markdown { "`currentColor` et `em` peuvent être utilisé comme des variables." }
                }
            }
        }

        // Step 2
        slide("Une aiguille", setOf("live-code")) {
            demo(2, data = mapOf("line" to "2,20-33"))
        }
        slide("Une aiguille - liens") {
            ul {
                markdown { "[CSS Positioning](https://developer.mozilla.org/en-US/docs/Learn/CSS/CSS_layout/Positioning)" }
                markdown { "[pseudo-éléments](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-elements)" }
                markdown { "[A Whole Bunch of Amazing Stuff Pseudo Elements Can Do](https://css-tricks.com/pseudo-element-roundup/)" }
//                markdown { "[Fluent 2014: Lea Verou, \"The Humble Border-Radius\"](https://www.youtube.com/watch?v=JSaMl2OKjfQ)" }
                markdown { "[`calc`](https://developer.mozilla.org/en-US/docs/Web/CSS/calc)" }
            }
        }

        // Step 3
        slide("La rotation", setOf("live-code")) {
            demo(3, data = mapOf("line" to "27-33,37-40"))
        }
        slide("La rotation - liens") {
            ul {
                markdown { "[CSS custom properties (variables)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)" }
                markdown { "[CSS transforms](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Transforms/Using_CSS_transforms)" }

                notice(Tips) {
                    markdown { "Les _custom properties_ sont héritées !" }
                    markdown { "L'unité `turn` est souvent plus intuitive que les autres unités d'angles." }
                }
            }
        }

        // Step 4
        slide("Heure et minutes", setOf("live-code")) {
            demo(4, data = mapOf("line" to "38-50"))
        }
        slide("Un peu de JavaScript") {
            sourceCode("code/setHourMinSec.js")
        }

        // Step 5
        slide("La trotteuse", setOf("live-code")) {
            demo(5) {
                """
                  |  <span class="pin hourhand"></span>
                  |  <span class="pin minutehand"></span>
                  |  <span class="pin secondhand"></span>""".trimMargin()
            }
            notes = """
                      | * Fast track for ::after, ::before
                      | * Issue, not very pleased by the setInterval
                      |""".trimMargin()
        }

        // Step 6
        slide("Les animations", setOf("live-code")) {
            demo(6) {
                """
                  |  <span class="pin hourhand"></span>
                  |  <span class="pin minutehand"></span>
                  |  <span class="pin secondhand"></span>""".trimMargin()
            }
            notes = """
                      | * hide other pin
                      | * Clean transform rotate, --amgle
                      | * Create rotatePin `@keyframes` rotate from `.5turn` to `1.5turn`
                      | * `animation: rotatePin 10s`
                      | * add linear timing
                      | * infinite
                      | * `animation-play-state: paused`
                      | * `animation-delay: -37s;`
                      | * use --duration, `60s`, `calc(60s * 60)`, `calc(60s * 60 * 12)`,
                      | * compute delay in JS `const delay = 60 * (min + 60 * h) + sec;`
                      | * `animation-delay: calc(-1s * var(--delay))`
                      | * remoge play-state;
                      |""".trimMargin()
        }
        slide("Encore du JavaScript") {
            sourceCode("code/setDelay.js")
        }
        slide("Les animations - liens") {
            ul {
                markdown { "[Using CSS animations](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Animations/Using_CSS_animations)" }
                markdown { "[`@keyframes`](https://developer.mozilla.org/en-US/docs/Web/CSS/@keyframes)" }
                markdown { "[_timing-function_](https://developer.mozilla.org/en-US/docs/Web/CSS/timing-function), [`cubic-bezier`](https://cubic-bezier.com/)" }
            }
        }

        // FIXME Step 7
        slide("Les marques", setOf("live-code")) {
            demo(7) {

                """${"  <span class=\"tick\"></span>\n".repeat(12)}
                  |
                  |  <span class="pin hourhand"></span>
                  |  <span class="pin minutehand"></span>
                  |  <span class="pin secondhand"></span>""".trimMargin()
            }
        }
        slide("Les marques - liens") {
            ul {
                notice(Warning) {
                    markdown { "[enable the use of `counter()` inside `calc()`](https://github.com/w3c/csswg-drafts/issues/1026)" }
                }
            }
        }
    }

    part("Support") {

        slide("Ça marche partout") {
            ul(steps = true) {
                markdown { "`border-radius`" }
                markdown { "..." }
                todo { "..." } // FIXME

                notice(Warning) {
                    html { "Pensez à l'accessibilité" }
                }
            }
        }
        slide("Ça sur les navigateurs modernes") {
            ul(steps = true) {
                markdown { "..." }
                todo { "..." } // FIXME

                notice(Warning) {
                    html { "PENSEZ vraiement à l'accessibilité !" }
                }
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
                html { "Maximiser `(wow)/(wtf)`" }
            }
        }

        slide("Présent, passé, et future") {
            ul(steps = true) {
                markdown { "🔮 Que sera le web demain ?" }
                markdown { "TODO citation Lorsque tu ne sais pas où tu vas, regarde d'où tu viens." }
                markdown { "Stablilité" }
                markdown { "Renaissance de Mozilla, Google avec Chrome" }
                markdown { "Compétition, standard, perfomance, évolution" }
                markdown { "Platforme universel" }
                markdown { "WASM & WebComponents ?" }
            }
        }

        slide("Merci") {
            h4("Questions ?")
            em { html { "(les retours sont bienvenus)" } }
        }
    }
}

fun ContainerBuilder.demo(
    step: Int,
    data: Map<String, String> = emptyMap(),
    inner: () -> String = { "" }
) {
    sourceCode("code/step$step.css", classes = setOf("line-numbers"), data = data)
    html {
        """<div class="demo">
             |  <div class="clock-step$step">${inner()}</div>
             |</div>""".trimMargin()
    }
}