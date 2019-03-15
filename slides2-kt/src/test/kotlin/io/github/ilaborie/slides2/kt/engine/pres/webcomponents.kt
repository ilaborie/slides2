import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.SlideEngine.run
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
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse
import io.github.ilaborie.slides2.kt.jvm.extra.CanIUse.Companion.caniuse
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet
import io.github.ilaborie.slides2.kt.jvm.extra.Tweet.Companion.tweet
import io.github.ilaborie.slides2.kt.jvm.jvmConfig


fun main() {
    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin, GridPlugin)
        .use(Tweet.Companion.TweetPlugin, CanIUse.Companion.CanIUsePlugin)
        .use(
            PrismJsPlugin(showLines = true, languages = listOf("typescript")),
            RoughSvgPlugin
        )

    val config = jvmConfig("presentations/WebComponents2019")
    val wcOut = run(config, webComponents, listOf(devoxxFr19))

    JvmFolder("public")
        .writeTextFile("data.json") {
            listOf(wcOut).joinToString(", ", "[ ", "]") { it.json }
        }
}


// https://shprink.github.io/talks/2018/web_component_native_vs_stenciljs

val webComponents = pres(id = "webComponents-19", extraStyle = "style", title = { mainTitle() }) {
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
            file("logos/toptal.svg")
            file("logos/monkeypatch.svg")
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
            todo { "Installation instructions, Wifi" } // TODO
        }
    }
    part("Modern Web Development Issues", skipHeader = true) {
        slide("I want to build a Web app in 2019", setOf("header-hidden")) {
            h4("I want to build a Web app in 2019")
        }
        slide("Choose Framework", setOf("header-hidden")) {
            h4("Let's start by picking up a Framework")
            ul(steps = true, classes = setOf("list-inline")) {
                inlineFigure("logos/react.svg", "React")
                inlineFigure("logos/vuejs.svg", "VueJs")
                inlineFigure("logos/angular.svg", "Angular")
            }
        }
        slide("Choose Style", styles = setOf("two-columns", "header-hidden")) {
            h4("Now let's select how to write our style")
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
            h4("Now let's transpile our code")
            ul(steps = true, classes = setOf("bullet")) {
                // TODO logo ?
                span("Webpack")
                span("ParcelJs")
                span("RollupJs")
                span("Bazel")
            }
        }
        slide("Development Not Easy", setOf("header-hidden")) {
            h5("Developing an app in JS in not easy anymore...")
        }
        slide("Industry Moving too Fast", setOf("header-hidden")) {
            h4("The industry is moving too fast...")
            tweet("540481335362875392")
        }
        slide("Interoperability", setOf("header-hidden")) {
            h4("...Interoperability is not available out of the box...")
            ul(classes = setOf("list-inline")) {
                inlineFigure("logos/react.svg", "React")
                inlineFigure("logos/vuejs.svg", "VueJs")
                inlineFigure("logos/angular.svg", "Angular")
            }
        }
        slide("Reinventing", setOf("header-hidden")) {
            h4("...And we keep reinventing the wheel!")
            ul(steps = true) {
                (1..10).forEach {
                    figure("img/material/material-design-$it.png", "Material $it")
                }
            }
        }
        slide("Solution", setOf("header-hidden")) {
            h4 {
                html { "All this complexity is coming to an end with" }
                strong("Web Components")
            }
        }
        slide("Size matters", setOf("header-hidden")) {
            val values = mapOf("Angular" to 59.0, "Stencil" to 11.0, "Native" to 2.5)
            val factor: (Double) -> Int = { (it * 2).toInt() }
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
                html { "😨 Stencil is 5 times smaller than Angular" }
                html { "😱 Native is 23 times smaller than Angular" }
                todo { "Add litElement, Update numbers" } // TODO
            }
        }
        slide("Time matters", setOf("header-hidden")) {
            barChart(
                "Time matters (FMP 3G 📱 in ms)",
                values = mapOf(
                    "Angular" to 3000,
                    "Stencil" to 1070,
                    "Native" to 1030,
                    "Stencil Pre Rendered" to 980
                ), unit = "ms",
                factor = { it },
                mode = BarChartSmallerBetter(fixedMin = 0)
            )
            ul(steps = true) {
                html { "😨 Native & Stencil 3 times faster than Angular" }
                todo { "Add litElement, Update numbers" } // TODO
            }
        }
    }
    part(partTitle = { webComponentTitle() }, id = "web_components_part") {
        slide("History", setOf("header-hidden")) {
            ul {
                markdown { "Specs from **World Wide Web Consortium** (W3C)" }
                markdown { "First draft in **2012**" }
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
            quote("Custom Elements is a capability for creating your own custom HTML elements with its own methods and properties")
        }
        slide("Shadow DOM", setOf("header-hidden")) {
            inlineFigure("img/webcomponents/shadow-DOM.svg", "Shadow DOM")
            quote("Shadow DOM provides encapsulation for DOM and CSS")
        }
        slide("HTML templates", setOf("header-hidden")) {
            inlineFigure("img/webcomponents/HTML-templates.svg", "HTML templates")
            quote("Give the ability to create reusable piece of HTML that can be used at runtime")
        }
        slide("Natif code", setOf("header-hidden")) {
            sourceCode("code/natif.js")
        }
        slide("Browser support", setOf("header-hidden")) {
            caniuse("Browser support",
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
            caniuse("Polyfill support",
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
                "Web component interoperability support",
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
                markdown { "**Open Source** project, [MIT License](https://github.com/ionic-team/stencil/blob/master/LICENSE)" }
                markdown { "Created by the **Ionic Team** in 2017" }
                markdown { "4.9k ⭐️ on github" }
            }
        }
        slide("Not a framework", setOf("header-hidden")) {
            h4 {
                html { "StencilJS is" }
                strong("not another framework")
            }
        }
        slide("Compiler", setOf("header-hidden")) {
            h4 {
                html { "StencilJS is a " }
                strong("compiler")
                html { "that generates " }
                strong("web components")
            }
        }
        slide("StencilJS is a set of great tools", setOf("header-hidden")) {
            table(
                "StencilJS is a **set** of **great tools**".markdown,
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
        slide("StencilJS works everywhere", setOf("header-hidden", "steps")) {
            h4 { markdown { "StencilJS **works everywhere**" } }
            p("Loads polyfills on-demand", classes = setOf("step"))
        }
        slide("Stencil is concise", setOf("header-hidden")) {
            figure("stencil/stencil-syntax.png", "Stencil Syntax is concise")
        }
        slide("Getting started") {
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
                markdown { "**Open Source** project, [BSD 3-Clause License](https://github.com/Polymer/lit-element/blob/master/LICENSE)" }
                markdown { "Created by the **Polymer Team** in 2017" }
                markdown { "1.7k ⭐️ on github" }
            }
        }
        slide("Close-to", setOf("header-hidden")) {
            h4("Close to other lightweight WebComponent frameworks")
            ul {
                link("https://stenciljs.com/", "Stencil")
                link("https://skatejs.netlify.com/", "SkateJS")
                link("https://svelte.technology/", "Svelte")
                link("http://slimjs.com", "Slim.js")
            }
        }
        slide("Templating", setOf("header-hidden")) {
            h4("Using the lit-html templating library")
            link("https://lit-html.polymer-project.org/")
            ul {
                markdown { "built on **HTML templates**" }
                markdown {
                    "with the ES2015 [Template literals](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Template_literals)"
                }
            }
        }
        slide("lit-html", setOf("header-hidden")) {
            sourceCode("code/lit-html.js")
        }
        slide("lit-elements in JS", setOf("header-hidden")) {
            markdown { "Just extend the `LitElement` class" }
            sourceCode("code/lit-element.js")
        }
        slide("lit-elements in TS", setOf("header-hidden")) {
            markdown { "Also can use Typescript with **decorators**" }
            sourceCode("code/lit-element.ts")
        }
        slide("lit-elements Support", setOf("header-hidden")) {
            p("The last 2 versions of all modern browsers are supported, including Chrome, Safari, Opera, Firefox, Edge. In addition, Internet Explorer 11 is also supported.")
            p("Edge and Internet Explorer 11 require the web components polyfills.")
        }
    }
    part("Workshop") {
        slide("TODO - work") {
            todo { "links..." } // TODO
            todo { "Hours pause ..." } // TODO
        }
    }
    part("Conclusion") {
        slide("Commons Issues") {
            ul {
                markdown { "Attribute are `string`" }
                markdown { "Styling with theme" }
                markdown { "Browser support" }
                todo { "others issues" }

                markdown { "Use an external state manager" }
                markdown { "Use CSS custom properties" }
                markdown { "Wait, or Electron, or polyfills" }
                todo { "others alternatives" }
            }
        }
        slide("Moderns Alternatives") {
            ul {
                link("https://skatejs.netlify.com/", "SkateJS")
                link("https://svelte.technology/", "Svelte")
                link("http://slimjs.com", "Slim.js")
                html { "..." }
            }
        }
        slide("End") { p("Thanks") }
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