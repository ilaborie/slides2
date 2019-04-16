import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.markdown
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.devoxxFr19
import io.github.ilaborie.slides2.kt.engine.contents.BarChart.Companion.BarChartCustom
import io.github.ilaborie.slides2.kt.engine.contents.BarChart.Companion.BarChartSmallerBetter
import io.github.ilaborie.slides2.kt.engine.contents.barChart
import io.github.ilaborie.slides2.kt.engine.contents.inlineFigure
import io.github.ilaborie.slides2.kt.engine.contents.speaker
import io.github.ilaborie.slides2.kt.engine.contents.table
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse.Companion.caniuse
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet.Companion.tweet
import io.github.ilaborie.slides2.kt.jvm.jvmConfig

private const val id = "webComponents-19"

fun main() {
    val config = jvmConfig("presentations/WebComponents2019")

    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin, GridPlugin)
        .use(Tweet.Companion.TweetPlugin, CanIUse.Companion.CanIUsePlugin)
        .use(PrismJsPlugin(showLines = true, languages = listOf("typescript")))
        .use(RoughSvgPlugin)
        .run(config, webComponents, listOf(devoxxFr19))

    val devoxxImgs = config.input / "img" / "devoxx"
    val outputDir = config.output / id
    listOf("background_v1.png", "background_v2.png", "background_white_v1.png", "footer_v2.png").forEach {
        devoxxImgs.copyOrUpdate(it, outputDir)
    }

    // Copy images
    (config.input / "img").copyOrUpdate("doitdoit.gif", config.output / id)
}

val webComponents = pres(id = id, extraStyle = "style", title = { mainTitle() }) {
    part("Introduction", skipHeader = true) {
        slide("Speakers", setOf("header-hidden")) {
            speaker(
                fullName = "Julien Renaux",
                classes = setOf("toptal"),
                src = "speakers/julien.jpg",
                info = "GDE Web, Freelancer",
                links = mapOf(
                    "@julienrenaux" to "https://twitter.com/julienrenaux",
                    "https://julienrenaux.fr/" to "https://julienrenaux.fr/"
                )
            )
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
        slide("Roadmap", setOf("roadmap")) {
            ul(classes = setOf("list-inline")) {
                span("Slides & Installation")
                span("Workshop")
                span("🌭")
                span("Workshop")
                span("Conclusion")
                span("...")
            }
        }
        slide("Instructions") {
            markdown { "#### Wifi `devoxxfr-hol` / `hola#4321`" }
            link("https://github.com/ilaborie/webcomponents-devoxx-19")
            markdown { "Puis dans les répertoires `native`, `stencil`, `lit-element`, faire" }
            markdown {
                """
                  |```bash
                  | $ npm ci
                  |```""".trimMargin()
            }
        }
    }
    part("Modern Web Development Issues", skipHeader = true) {
        slide("I want to build a Web app in 2019", setOf("header-hidden")) {
            h4("Développons une application Web en 2019")
        }
        slide("Choose Framework", setOf("header-hidden")) {
            h4("Commençons par choisir un Framework")
            ul(steps = true, classes = setOf("list-inline")) {
                inlineFigure("logos/react.svg", "React")
                inlineFigure("logos/vuejs.svg", "VueJs")
                inlineFigure("logos/angular.svg", "Angular")
            }
        }
        slide("Choose Style", styles = setOf("two-columns", "header-hidden")) {
            h4("Puis comment allons-nous écrire le style")
            ul(steps = true, classes = setOf("bullet")) {
                span("CSS")
                span("Sass/Scss")
                span("Less")
                span("Stylus")
                span("CSS-in-JS")
                span("PostCSS")
                span("NextCSS")
                span("...")
            }
        }
        slide("Choose JavaScript Transpiler", setOf("header-hidden")) {
            h4("Puis construisons notre application")
            ul(steps = true, classes = setOf("bullet")) {
                span("Webpack")
                span("ParcelJs")
                span("RollupJs")
                span("Bazel")
            }
        }
        slide("Development Not Easy", setOf("header-hidden")) {
            h5("Développer une application en JS n'est plus simple...")
        }
        slide("Industry Moving too Fast", setOf("header-hidden")) {
            h4("Ça va trop vite ...")
            tweet("540481335362875392")
        }
        slide("Interoperability", setOf("header-hidden")) {
            h4("...Interopérabilité ne vient plus gratuitement...")
            ul(classes = setOf("list-inline")) {
                inlineFigure("logos/react.svg", "React")
                inlineFigure("logos/vuejs.svg", "VueJs")
                inlineFigure("logos/angular.svg", "Angular")
            }
        }
        slide("Reinventing", setOf("header-hidden")) {
            h4("...et on réinvente sans arrêt la roue !")
            ul(steps = true) {
                (1..10).forEach {
                    figure("img/material/material-design-$it.png", "Material $it")
                }
            }
        }
        slide("Reinventing 2", setOf("header-hidden")) {
            ul(steps = true) {
                figure("img/material-add.png", "Material Web Components")
                link("https://github.com/material-components/material-components-web-components")
            }
        }
        slide("Solution", setOf("header-hidden")) {
            markdown { "#### Toute cette complexité doit s'arrêter avec les **Web Components**" }
        }
        slide("Size matters", setOf("header-hidden")) {
            val values = mapOf(
                "Angular" to 59.0,
                "Stencil" to 8.8,
                "LitElement" to 8.4,
                "Native" to 2.4
            )
            val factor: (Double) -> Int = { (it * 10).toInt() }
            barChart(
                "Size matters (Gzipped)",
                values = values,
                unit = "kb",
                factor = factor,
                mode = BarChartCustom(
                    min = 0,
                    max = factor(59.0), // worst value
                    low = factor(15.0),
                    high = factor(25.0),
                    optimum = factor(2.5) // best value
                )
            )
            ul(steps = true) {
                markdown { "😨 Stencil et litElement sont 5 fois plus petits qu'Angular" }
                markdown { "😱 Native est 23 fois plus petit qu'Angular" }
            }
        }
        slide("Time matters", setOf("header-hidden")) {
            barChart(
                "Le temps ça compte (FMP 3G 📱 en ms)",
                values = mapOf(
                    "Angular" to 2957,
                    "LitElement" to 1125,
                    "Stencil" to 1129,
                    "Native" to 991,
                    "Stencil Pre Rendered" to 869
                ), unit = "ms",
                factor = { it },
                mode = BarChartSmallerBetter(fixedMin = 0)
            )
            ul(steps = true) {
                markdown { "😨 Angular est 3 fois plus lent" }
            }
        }
    }
    part(partTitle = { webComponentTitle() }, id = "web_components_part") {
        slide("History", setOf("header-hidden")) {
            ul {
                markdown { "Spécifier par le **World Wide Web Consortium** (W3C)" }
                markdown { "Débuté en **2012**" }
            }
        }
        slide("Components", setOf("header-hidden")) {
            ul(steps = true, classes = setOf("list-inline")) {
                inlineFigure("img/webcomponents/custom-elements.svg", "Custom Elements")
                inlineFigure("img/webcomponents/shadow-DOM.svg", "Shadow DOM")
                inlineFigure("img/webcomponents/HTML-templates.svg", "HTML templates")
                inlineFigure("img/webcomponents/HTML-imports.svg", "HTML imports")
                html { "<div class=\"obsolete\"></div>" }
            }
        }
        slide("Custom Elements", setOf("header-hidden")) {
            inlineFigure("img/webcomponents/custom-elements.svg", "Custom Elements")
            quote {
                markdown {
                    "Les _Custom Elements_ sont la capacité de créer un balise HTML avec ses propres attributs et méthodes"
                }
            }
        }
        slide("Shadow DOM", setOf("header-hidden")) {
            inlineFigure("img/webcomponents/shadow-DOM.svg", "Shadow DOM")
            quote {
                markdown {
                    "Le Shadow DOM fournit l'encapsulation du DOM et du CSS"
                }
            }
        }
        slide("HTML templates", setOf("header-hidden")) {
            inlineFigure("img/webcomponents/HTML-templates.svg", "HTML templates")
            quote("Définit un bloc d'HTML réutilisable au moment de l'exécution")
        }
        slide("Natif code", setOf("header-hidden")) {
            sourceCode("code/natif.js")
        }
        slide("Shadow DOM Exemple", setOf("header-hidden")) {
            figure("img/shadowDom.png", "Shadow Dom")
        }
        slide("Browser support", setOf("header-hidden")) {
            caniuse("Support des navigateurs",
                    features = listOf("custom-elementsv1", "shadowdomv1", "template"),
                    browsers = listOf("ie" to 11, "edge" to 18, "firefox" to 65, "chrome" to 72, "safari" to 12),
                    browserFn = { (name, version) ->
                        {
                            inlineFigure("browsers/$name.svg", name)
                            html { version.toString() }
                        }
                    },
                    statusFn = { status ->
                        {
                            html {
                                when (status) {
                                    "y" -> "😃"
                                    "p", "n" -> "😫"
                                    "a" -> "🙂"
                                    else -> "🤔"
                                }
                            }
                        }
                    })
            link("https://caniuse.com")
        }
        slide("Polyfill support", setOf("header-hidden")) {
            caniuse("Support avec le polyfill",
                    features = listOf("custom-elementsv1", "shadowdomv1", "template"),
                    browsers = listOf("ie" to 11, "edge" to 18, "firefox" to 65, "chrome" to 72, "safari" to 9),
                    browserFn = { (name, version) ->
                        {
                            inlineFigure("browsers/$name.svg", name)
                            html {
                                when (name) {
                                    "safari", "ie" -> "$version+"
                                    else           -> "&nbsp;"
                                }
                            }
                        }
                    },
                    statusFn = { { html { "✔︎" } } })
            link("https://github.com/webcomponents/webcomponentsjs")
        }
        slide("Framework Interoperability", setOf("header-hidden")) {
            barChart(
                "Interopérabilité des Web Component",
                values = mapOf(
                    "Angular 6" to 100,
                    "Vue" to 100,
                    "AngularJS" to 100,
                    "Preact" to 91,
                    "React" to 71
                ),
                factor = { it },
                infoFn = { "$it%" },
                mode = BarChartCustom(
                    min = 0,
                    max = 100,
                    low = 80,
                    high = 95,
                    optimum = 100
                )
            )
            link("https://custom-elements-everywhere.com/")
        }
    }
    part(partTitle = { stencilTitle() }, id = "stenciljs_part") {
        slide("What", setOf("header-hidden")) {
            ul(steps = true) {
                markdown { "Projet **Open Source**, [MIT License](https://github.com/ionic-team/stencil/blob/master/LICENSE)" }
                markdown { "Créé par l'équipe d'**Ionic** en 2017" }
                markdown { "5.2k ⭐️ sur github" }
            }
        }
        slide("Not a framework", setOf("header-hidden")) {
            markdown {
                "#### StencilJS n'est **pas un autre framework**"
            }
        }
        slide("Compiler", setOf("header-hidden")) {
            markdown {
                "#### StencilJS c'est un **compileur** qui génère des **web components** "
            }
        }
        slide("StencilJS is a set of great tools", setOf("header-hidden")) {
            table(
                "StencilJS c'est un **ensemble** de **bons outils**".markdown,
                rows = listOf("JSX / Virtual DOM", "TypeScript", "Decorators", "Prerendering SSR"),
                columns = listOf("Web Components", "Angular", "React", "Stencil"),
                values = mapOf(
                    ("JSX / Virtual DOM" to "Web Components") to false,
                    ("JSX / Virtual DOM" to "Angular") to false,
                    ("JSX / Virtual DOM" to "React") to true,
                    ("JSX / Virtual DOM" to "Stencil") to true,

                    ("TypeScript" to "Web Components") to false,
                    ("TypeScript" to "Angular") to true,
                    ("TypeScript" to "React") to false,
                    ("TypeScript" to "Stencil") to true,

                    ("Decorators" to "Web Components") to false,
                    ("Decorators" to "Angular") to true,
                    ("Decorators" to "React") to false,
                    ("Decorators" to "Stencil") to true,

                    ("Prerendering SSR" to "Web Components") to false,
                    ("Prerendering SSR" to "Angular") to false,
                    ("Prerendering SSR" to "React") to false,
                    ("Prerendering SSR" to "Stencil") to true
                ),
                columnFn = { col ->
                    val key = when (col) {
                        "Web Components" -> "web-components"
                        "Angular"        -> "angular"
                        "React"          -> "react"
                        "Stencil"        -> "stencil"
                        else             -> throw IllegalArgumentException("Unexpected value $col")
                    }
                    compound { inlineFigure("logos/$key.svg", col) }
                },
                valueFn = { value -> compound { html { if (value) "✔︎" else "✘" } } },
                valueClassesFn = { value -> setOf(if (value) "y" else "n") }

            )
        }
        slide("StencilJS works everywhere", setOf("header-hidden")) {
            ul(steps = true) {
                markdown { "#### StencilJS **marche partout**" }
                markdown { "Il charge les polyfills à la demande" }
            }
        }
        slide("Stencil is concise", setOf("header-hidden")) {
            figure("img/stencil/stencil-syntax.png", "La syntaxe de Stencil est concise")
        }
        slide("Pour démarrer") {
            code("sh") { "\$ npm init stencil" }
            code("sh") {
                """? Pick a starter › - Use arrow-keys. Return to submit.
                  |❯  ionic-pwa     Everything you need to build fast, production ready PWAs
                  |   app           Minimal starter for building a Stencil app or website
                  |   component     Collection of web components that can be used anywhere""".trimMargin()
            }
        }
        slide("stencil code", setOf("header-hidden")) {
            sourceCode("code/stencil.tsx")
        }
    }
    part(partTitle = { litElementTitle() }, id = "lit-element_part") {
        slide("What-Lit", setOf("header-hidden")) {
            ul(steps = true) {
                markdown { "Projet **Open Source**, [BSD 3-Clause License](https://github.com/Polymer/lit-element/blob/master/LICENSE)" }
                markdown { "Créer par l'équipe **Polymer Team** en 2017" }
                markdown { "1.9k ⭐️ sur github" }
            }
        }
        slide("Templating", setOf("header-hidden")) {
            markdown {
                "#### Utilise la bibliothèque de template **lit-html**"
            }
            link("https://lit-html.polymer-project.org/")
            ul {
                markdown { "4.3k ⭐️ sur github" }
                markdown { "Basé sur les **templates HTML**" }
                markdown {
                    "Avec les [_Template literals_](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Template_literals) de ES2015"
                }
            }
        }
        slide("lit-html", setOf("header-hidden")) {
            sourceCode("code/lit-html.js")
        }
        slide("lit-elements in JS", setOf("header-hidden")) {
            //            markdown { "Juste étendre la classe `LitElement`" }
            sourceCode("code/lit-element.js")
        }
        slide("lit-elements in TS", setOf("header-hidden")) {
            //            markdown { "On peut aussi utiliser les **decorateurs** de Typescript" }
            sourceCode("code/lit-element.ts")
        }
        slide("lit-elements Support", setOf("header-hidden")) {
            markdown {
                """✅ Chrome, Safari, Opera, Firefox
                  |
                  |[polyfills](https://github.com/webcomponents/webcomponentsjs)
                  |pour Edge et IE 11""".trimMargin()
            }
        }
    }
    part("Workshop") {
        slide("Exercises", setOf("header-hidden")) {
            figure("exo/exercises.svg", "Exercises")
            link("http://bit.ly/devoxx-webc")
        }
    }
    part("Conclusion") {
        slide("Les limites") {
            ul {
                markdown { "Support des navigateurs" }
                markdown { "Gestion de l'état d'une application" }
                markdown { "Thème" }
                markdown { "Utilisez les _custom properties_ CSS " }
                markdown { "Utiliser le polyfill ou Electron" }
            }
            todo { "..." } // TODO
        }
        slide("Les alternatives modernes") {
            ul {
                markdown { "[SkateJS](https://skatejs.netlify.com/)" }
                markdown { "[Svelte](https://svelte.technology/)" }
                markdown { "[Slim.js](http://slimjs.com)" }
                markdown { "..." }
            }
        }
        slide("Le future") {
            h4("🔮")
            ul {
                markdown { "Pseudo elements avec [`::part` and `::theme`, an ::explainer](https://meowni.ca/posts/part-theme-explainer/), [CSS Shadow Parts](https://www.w3.org/TR/css-shadow-parts-1/)" }
                markdown { "Pseudo elements avec [`::part` and `::theme`, an `::explainer`](https://meowni.ca/posts/part-theme-explainer/), [CSS Shadow Parts](https://www.w3.org/TR/css-shadow-parts-1/)" }
                markdown { "[Scoped Custom Element Registries](https://github.com/w3c/webcomponents/issues/716)" }
                markdown { "SSR pour lit-html et litElement" }
                markdown { "..." }
            }
        }
        slide("Des liens") {
            ul {
                markdown { "[Web Components sur MDN](https://developer.mozilla.org/en-US/docs/Web/Web_Components)" }
                markdown { "[Série d'articles sur _css-tricks_](https://css-tricks.com/an-introduction-to-web-components/)" }
                markdown { "[A curated list of awesome lit-html resources.](https://github.com/web-padawan/awesome-lit-html)" }
                markdown { "[Web Components Todo](https://wc-todo.firebaseapp.com/)" }
                markdown { "[🚧 (WIP) Les articles de Ph. Charrière](https://k33g.gitlab.io/BLOG.html#articles-from-this-blog)" }
                todo { "Qui suivre sur Twitter ?" }
            }
        }
        slide("Fin") {
            markdown {
                """
                #### Merci

                Pensez à nous faire des retours (votez !)
            """.trimIndent()
            }
        }
    }
}

private fun ContainerBuilder.litElementTitle() {
    h2("Lit-Elements")
    figure("logos/lit-element.svg", "LitElement")
    link("https://lit-element.polymer-project.org/")
}

private fun ContainerBuilder.stencilTitle() {
    h2("StencilJS")
    inlineFigure("logos/stencil.svg", "StencilJS")
    link("https://stenciljs.com/")
}

private fun ContainerBuilder.webComponentTitle() {
    h2("Web Components")
    inlineFigure("logos/web-components.svg", "Web Components")
}

private fun ContainerBuilder.mainTitle() {
    h1("Web Components")
    ul(classes = setOf("list-inline")) {
        listOf(
            inlineFigure("logos/web-components.svg", "Natif"),
            inlineFigure("logos/stencil.svg", "StencilJS"),
            figure("logos/lit-element.svg", "LitElement")
        )
    }
}