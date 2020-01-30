import io.github.ilaborie.slides2.kt.SlideEngine
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
            listOf(
                Script.script("cssClockwork.js"),
                Script.script("player.js")
            )
    }

    SlideEngine
        .use(CheckContentPlugin)
        .use(
            TocPlugin,
            NavigatePlugin,
            PrismJsPlugin(showLines = false, lineHighlight = true),
            MathJaxPlugin(),
            cssClockworkPlugin
        )
        .run(config, cssClockwork, listOf(Theme.tnt))


    val outputDir = config.output / id
    // Copy video
    val videoDir = config.input / "video"
    listOf("giphy.mp4").forEach {
        videoDir.copyOrUpdate(it, outputDir)
    }
    // Copy script
    val scriptDir = config.input / "script"
    listOf("cssClockwork.js", "player.js").forEach {
        scriptDir.copyOrUpdate(it, outputDir)
    }
    // Copy img
    val imgDir = config.input / "logos"
    listOf("bg-gdg.png", "toulouse-skyline.png").forEach {
        imgDir.copyOrUpdate(it, outputDir)
    }
}

private val cssClockwork = pres(
    id = id,
    title = "⏰ CSS Clockworks",
    extraStyle = "style"
) {
    part("Introduction", skipHeader = true) {
        slide("Safety Last - 1923", id = "safetylast") {
            html {
                """
                 <div id="player"></div>
                 <div class="message">
                    <p>Ce live-coding est réalisé par un professionnel, 
                       il est chaudement recommandé de <strong>reproduire</strong> ce type de code chez vous !</p>
                 </div>
                 <script src="https://www.youtube.com/iframe_api"></script> 
                 """.trimIndent()
            }
        }
        slide("Speakers", setOf("header-hidden", "steps")) {
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
            html { "<h4 class=\"designer step\">I'm not a designer <span class=\"blink step\">!</span></h4>" }
        }
        slide("CSS is Awesome!") {
            quote(classes = setOf("step")) {
                markdown { "The [Rule of Least Power](https://www.w3.org/2001/tag/doc/leastPower.html) suggests choosing the least powerful language suitable for a given purpose" }
            }
            ul(steps = true) {
                markdown { "Bien connaître les [sélecteurs](https://developer.mozilla.org/fr/docs/Web/CSS/S%C3%A9lecteurs_CSS), et les [unités](https://developer.mozilla.org/fr/docs/Web/CSS/length)" }

                markdown { "Évitez trop d'adhérences aux pré-processeurs" }
                markdown { "[Flexbox](https://developer.mozilla.org/fr/docs/Web/CSS/CSS_Flexible_Box_Layout) et [CSS Grid](https://developer.mozilla.org/fr/docs/Web/CSS/CSS_Grid_Layout) sont géniaux !" }
                markdown { "Les [pseudo-éléments](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-elements) `::before` et `::after` sont géniaux !" }
                markdown { "Les [pseudo-classes](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-classes) comme `:checked` c'est puissant" }
                strong("Traitez le CSS comme du code !")
                link("https://www.youtube.com/watch?v=fPObs60585w", "CSS is Awesome au Devfest Toulouse 2017")
                link("http://www.monkeypatch.io/blog/2017/2017-05-02-makingof_css_is_awesome/", "Blog  Making Of")
            }
        }
//        slide("🦄 forever") {
//            file("code/unicorn.html")
//        }
    }

    part("Live-coding") {
        // Step 1
        slide("Le cadre", setOf("live-code")) {
            demo(1)

            notes = """
                      | * using 1em as size
                      | * using `currentColor` as border `.05em`
                      | * radius 50%
                      | * `box-sizing: border-box`
                      | * box-shadow: `.05em .05em .1em rgba(0, 0, 0, .5)`
                      | * spread-radius et inset `inset 0 0 0 .02em white`
                      | *    `inset .05em .05em .1em rgba(0, 0, 0, .5)`
                      | *    `inset .5em .5em 1em rgba(0, 0, 0, .1)`
                      |""".trimMargin()
        }
        slide("La cadre - liens") {
            ul {
                ul {
                    markdown { "L'élément [`<time>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/time) existe !" }
                    markdown { "[`currentColor`](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value#currentColor_keyword)" }
                    markdown { "[`border-radius`](https://developer.mozilla.org/en-US/docs/Web/CSS/border-radius)" }
                    markdown { "[`box-sizing`](https://developer.mozilla.org/en-US/docs/Web/CSS/box-sizing)" }
                    markdown { "[`box-shadow`](https://developer.mozilla.org/en-US/docs/Web/CSS/box-shadow)" }
                }
                notice(Tips) {
                    markdown { "`currentColor` et `em` peuvent être utilisés comme des variables." }
                }
            }
        }

        // Step 2
        slide("Une aiguille", setOf("live-code")) {
            demo(2, data = mapOf("line" to "2,20-33"))
            notes = """
                      | * `::before`
                      | * `width: .02em`
                      | * `height: .4em;`
                      | * position relative & absolute
                      | * top: 50%
                      | * left: calc(50% - .01em); // -1/2 width
                      | * border-bottom-{left,right}-radius: 100% 80%
                      |""".trimMargin()
        }
        slide("Une aiguille - liens") {
            ul {
                markdown { "[CSS Positioning](https://developer.mozilla.org/en-US/docs/Learn/CSS/CSS_layout/Positioning)" }
                markdown { "[pseudo-éléments](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-elements)" }
                markdown { "[A Whole Bunch of Amazing Stuff Pseudo Elements Can Do](https://css-tricks.com/pseudo-element-roundup/)" }
//                markdown { "[Fluent 2014: Lea Verou, \"The Humble Border-Radius\"](https://www.youtube.com/watch?v=JSaMl2OKjfQ)" }
                markdown { "[`calc`](https://developer.mozilla.org/en-US/docs/Web/CSS/calc)" }
                notice(Tips) {
                    markdown { "On retrouve souvent le positionnement `absolute` avec les pseudo-éléments" }
                }
            }
        }

        // Step 3
        slide("La rotation", setOf("live-code")) {
            demo(3, data = mapOf("line" to "27-33,39-40"))

            notes = """
                      | * use var width, height
                      | * `left: calc(50% - var(--width) / 2);`
                      | * transform: rotate(10deg);
                      | * `transform-origin: top center`
                      | * `--angle: calc(1turn / 60 * 37 - .5turn)` // 37 min
                      |""".trimMargin()
        }
        slide("La rotation - liens") {
            ul {
                markdown { "[CSS custom properties (variables)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)" }
                markdown { "[CSS transforms](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Transforms/Using_CSS_transforms)" }

                notice(Tips) {
                    markdown {
                        """Les _custom properties_ sont héritées, <br>
                        |très utiles dans les _Web Components_ avec le _Shadow DOM_""".trimMargin()
                    }
                    markdown { "L'unité `turn` est souvent plus intuitive que les autres unités d'angles." }
                }
            }
        }

        // Step 4
        slide("Heure et minutes", setOf("live-code")) {
            demo(4, data = mapOf("line" to "38-50"))

            notes = """
                      | * hour: --width: .035em
                      | *       --height: .2em;
                      | *       --angle: calc(1turn / 12 * var(--hour) - .5turn);
                      | * min:  --width: .02em;
                      | *       --height: .3em;
                      | *       --angle: calc(1turn / 60 * var(--minute) - .5turn);
                      |""".trimMargin()
        }
        slide("Un peu de JavaScript") {
            sourceCode("code/setHourMinSec.js")
        }

        // Step 5
        slide("La trotteuse", setOf("live-code")) {
            demo(5, data = mapOf("line" to "21,37-38,41-84")) {
                """
                  |  <span class="pin hourhand"></span>
                  |  <span class="pin minutehand"></span>
                  |  <span class="pin secondhand"></span>""".trimMargin()
            }

            notes = """
                      | * use --hour, --minute, --second
                      | * second w: .015em, h: .4em
                      | * Fast track for ::after, ::before
                      | *    `::before` circle `r: 2*width`, `top: -1 width`, `left: -1/2 width`
                      | *    `::after` `width: 100%`, `height: 2*width`, `top: -1 width`, `left: -1/2 width`
                      | * Issue, not very pleased by the setInterval
                      |""".trimMargin()
        }
        slide("La trotteuse - liens") {
            ul {
                markdown { "Les [fonctions trigonometriques du CSS](https://github.com/w3c/csswg-drafts/issues/2331#issuecomment-467990627) ne sont pas encore disponibles" }

                notice(Tips) {
                    markdown {
                        """On peut utiliser un pre-processeur, ou du JS, pour remplacer ces fonctions""".trimMargin()
                    }
                    markdown {
                        """🤓 Ou les formules [Taylor / Maclaurin](https://fr.wikipedia.org/wiki/S%C3%A9rie_de_Taylor) avec des _custom properties_, voir cet [exemple](https://gist.github.com/stereokai/7666bfe93929b14c2dced148c79e0e97)."""
                    }
                }
            }
        }

        // Step 6
        slide("Les animations", setOf("live-code")) {
            demo(6, data = mapOf("line" to "21-29,49-55")) {
                """
                  |  <span class="pin hourhand"></span>
                  |  <span class="pin minutehand"></span>
                  |  <span class="pin secondhand"></span>""".trimMargin()
            }
            notes = """
                      | * hide other pin
                      | * Clean transform rotate, --angle
                      | * Create rotatePin `@keyframes` rotate from `.5turn` to `1.5turn`
                      | * `animation: rotatePin 10s`
                      | * add linear timing
                      | * infinite
                      | * `animation-play-state: paused`
                      | * `animation-delay: -37s;`
                      | * use --duration, `60s`, `calc(60s * 60)`, `calc(60s * 60 * 12)`,
                      | * compute delay in JS `const delay = 60 * (min + 60 * h) + sec;`
                      | * `animation-delay: calc(-1s * var(--delay))`
                      | * remove play-state;
                      |""".trimMargin()
        }
        slide("Les animations - liens") {
            ul {
                markdown { "[Using CSS animations](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Animations/Using_CSS_animations)" }
                markdown { "[`@keyframes`](https://developer.mozilla.org/en-US/docs/Web/CSS/@keyframes)" }
                markdown { "[_timing-function_](https://developer.mozilla.org/en-US/docs/Web/CSS/timing-function)" }
                markdown { "[`cubic-bezier`](https://cubic-bezier.com/)" }
            }
        }

        // Step 7
        slide("Les marqueurs", setOf("live-code")) {
            demo(7, data = mapOf("line" to "19,107-136")) {
                """${(1..12).joinToString("\n  ") { "  <span class=\"tick\" style=\"--count: $it\"></span>\n" }}
                  |
                  |  <span class="pin hourhand"></span>
                  |  <span class="pin minutehand"></span>
                  |  <span class="pin secondhand"></span>""".trimMargin()
            }

            notes = """
                      | * emmet `span.tick[style="--count: ${'$'}"]*12`
                      | *  `upper-roman`
                      | * Fast track
                      | ```css
                      | /* Ticks */
                      | .clock .tick {
                      |     background: teal;
                      |     position: absolute;
                      |     top: 50%;
                      |     left: calc(50% - .01em);
                      |     height: .4em;
                      |     width: .02em;
                      |     opacity: .5;
                      |     transform-origin: top center;
                      |     transform: rotate(calc(.5turn + 1turn / 12 * var(--count)));
                      | }
                      |
                      | .clock .tick::before {
                      |     position: absolute;
                      |     display: block;
                      |     font-size: 8%;
                      |     top: calc(100% - .8em);
                      |     transform: translateX(-40%) rotate(.5turn);
                      |     text-align: center;
                      |
                      |     content: 'W';
                      | }
                      | ```
                      |
                      | * counter-increment, counter(tick), counter reset
                      | * upper-roman
                      | * opacity .25, :nth-child(3n), .8
                      |""".trimMargin()
        }
        slide("Les marqueurs - liens") {
            ul {
                markdown { "[Emmet](https://docs.emmet.io/cheat-sheet/)" }
                markdown { "[Pseudo-classe `:nth-child()`](https://developer.mozilla.org/en-US/docs/Web/CSS/:nth-child)" }
                markdown { "[Using CSS counters](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Lists_and_Counters/Using_CSS_counters)" }
                markdown { "[`@counter-style`](https://developer.mozilla.org/en-US/docs/Web/CSS/@counter-style) (que sur Firefox)" }
                markdown { "[Voir sur codepen](https://codepen.io/ilaborie/pen/wRpoJy)" }

                notice(Warning) {
                    markdown { "[Enable the use of `counter()` inside `calc()`](https://github.com/w3c/csswg-drafts/issues/1026)" }
                }
            }
        }
    }

    part("Support") {

        slide("Ça marche partout") {
            link("http://caniuse.com")
            ul(steps = true) {
                markdown { "`currentColor`, `border-radius`, `box-shadow`, `box-sizing`," }
                markdown { "_CSS Generated content for pseudo-elements_, `calc`," }
                markdown { "_CSS3 2D Transforms_, _CSS Animation_" }
            }
        }
        slide("Ça sur les navigateurs modernes") {
            ul(steps = true) {
                markdown { "_CSS Variables (Custom Properties)_" }
                figure("logos/ie.svg", "😡 Pas sur IE")
                markdown { "Alternative statique: [postcss-preset-env](https://preset-env.cssdb.org/)" }
                markdown { "Alternative dynamique: JavaScript" }
                markdown { "_Polyfill_: [css-vars-ponyfill](https://jhildenbiddle.github.io/css-vars-ponyfill)" }
            }
        }
        slide("Rappel Accessibilité") {
            ul(steps = true) {
                figure("imgs/support-custom-properties.png", "https://caniuse.com/#feat=css-variables")
                markdown { "Utilisateurs de IE 11 en France: <mark>0,84%</mark>" }
                markdown { "Personnes atteintes d'un trouble de la vision en France: <mark>2,5%</mark>" }
                markdown { "Rendre accessibles vos sites c'est traité ces 2 situations" }
                notice(Warning) {
                    markdown { "️ **Revoyez vos priorités** ❗️" }
                    markdown { "<small>Voir [Quelques chiffres sur la déficience visuelle](https://www.aveuglesdefrance.org/quelques-chiffres-sur-la-deficience-visuelle)</small>" }
                }
            }
        }
    }

    part("Conclusion") {
        slide("À quoi ça sert ?") {
            ul(steps = true) {
                markdown { "À rien." }
                //                file("code/clock-unicorn.html")
            }
        }

        slide("Les moments 'WTF'") {
            html { """<video autoplay loop src="giphy.mp4" type="video/mp4"></video>""" }
        }

        slide("Pourquoi en est on là ?") {
            ul(steps = true) {
                markdown { "🕳 <del>Incompatibilité</del>" }
                markdown { "📄 <del>Documentation</del> voir [MDN](https://developer.mozilla.org/en-US/)" }
                markdown { "⌛️ Pas le temps sur les projets" }
                markdown { "🚫 pas de cours CSS" }
                markdown { "😡 technologie mésestimée" }
                markdown { "🛑 **Il faut changer cela !**" }
            }
        }

        slide("Comment apprendre") {
            ul(steps = true) {
                markdown { "🤹‍♀️️ regardez des présentations 🤹‍♂️" }
                markdown { "🎓 mentoring" }
                markdown { "📚 livres, par exemple [CSS Secrets](http://www.amazon.com/CSS-Secrets-Lea-Verou/dp/1449372635?tag=leaverou-20)" }
                markdown { "... les _side-projects_" }
                html { "Maximiser `(wow)/(wtf)`" }
            }
        }

        slide("Passé, présent et future") {
            ul(steps = true) {
                markdown { "🔮 Que sera le _web_ demain ?" }
                quote("Lorsque tu ne sais pas où tu vas, regarde d'où tu viens.")
                markdown { "🧱 Donnez-vous du temps pour les bases: HTML, CSS, JS" }
            }

            notes = """
                      | * Moyen age: IE
                      | * Renaissance: Mozilla, puis Chrome & Smartphone
                      | * compétition: perf JS, standard
                      | * évolution: HTML, ES2015, ...
                      | * Platform universel
                      | * Hope: WASM & WebComponents
                      |""".trimMargin()
        }

        slide("Merci") {
            ul {
                h4("Questions ?")
                em { html { "(les retours sont bienvenus)" } }
            }
            qrCode("https://ilaborie.github.io/slides2/cssClockwork/index-tnt.html")
        }
    }
}
