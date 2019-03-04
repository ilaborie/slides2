import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.dsl.raw
import io.github.ilaborie.slides2.kt.engine.extra.barChart
import io.github.ilaborie.slides2.kt.engine.extra.inlineFigure
import io.github.ilaborie.slides2.kt.engine.extra.speaker


// https://shprink.github.io/talks/2018/web_component_native_vs_stenciljs


val webComponents = pres(
    id = "webComponents-19",
    extraStyle = "style",
    title = {
        h1("Web Components")
        ul(classes = setOf("list-inline")) {
            listOf(
                figure("logos/web-components.svg", "Natif"),
                figure("logos/stencil.svg", "StencilJS"),
                figure("logos/lit-element.png", "LitElement")
            )
        }
    }
) {
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
                span("⏸")
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
                figure("logos/react.svg", "React")
                figure("logos/vuejs.svg", "VueJs")
                figure("logos/angular.svg", "Angular")
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
                // TODO logo
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
//            tweet("540481335362875392")
        }
        slide("Interoperability", setOf("header-hidden")) {
            h4("...Interoperability is not available out of the box...")
            ul(classes = setOf("list-inline")) {
                figure("logos/react.svg", "React")
                figure("logos/vuejs.svg", "VueJs")
                figure("logos/angular.svg", "Angular")
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
            ul(steps = true) {
                barChart(
                    "Size matters (Gzipped)",
                    mapOf( // TODO icon
                        "Angular" to 59.0,
                        "Stencil" to 11.0,
                        "Native" to 2.5
                    ),
                    unit = "kb",
                    factor = { (it * 2).toInt() }
                )
                html { "😨 Stencil is 5 times smaller than Angular" } // TODO icon
                html { "😱 Native is 23 times smaller than Angular" } // TODO icon
            }
        }
        slide("Time matters", setOf("header-hidden")) {
            ul(steps = true) {
                barChart(
                    "Time matters (FMP 3G 📱 in ms)", mapOf(
                        "Angular" to 3000.0,
                        "Stencil" to 1070.0,
                        "Native" to 1030.0,
                        "Stencil Pre Rendered" to 980.0
                    ), unit = "ms"
                )
                html { "😨 Native & Stencil 3 times faster than Angular" }
            }
        }
    }
    part(partTitle = {
        h2("Web Components")
        figure("logos/web-components.svg", "Web Components")
    }, id = "web_components_part") {
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
        slide("Browser support", setOf("header-hidden")) {
            // custom-elementsv1, shadowdomv1, template
            // ie11, edge18, firefox65, chrome72, opera58\
            file("img/caniuse/browser-support.html")
        }
        slide("Polyfill support", setOf("header-hidden")) {
            file("img/caniuse/polyfill-support.html")
            link("https://github.com/webcomponents/webcomponentsjs")
        }
        slide("Framework Interoperability", setOf("header-hidden")) {
            file("img/caniuse/frameworks-support.html")
            link("https://custom-elements-everywhere.com/")
        }
    }
    part(partTitle = {
        h2("StencilJS")
        figure("logos/stencil.gif", "StencilJS")
        link("https://stenciljs.com/")
    }, id = "stenciljs_part") {

        slide("What", setOf("header-hidden")) {
            ul(steps = true) {
                markdown { "**Open Source** project" }
                markdown { "Created by the **Ionic Team** in 2017" }
                markdown { "3.9k ⭐️ on github" }
                markdown { "3.9k ⭐️ on github" }
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
        slide("StencilJS is a **set** of **great tools**", setOf("header-hidden")) {
            table(
                "StencilJS is a set of great tools",
                rows = listOf("JSX / Virtual DOM", "TypeScript", "Decorators", "Prerendering SSR"),
                columns = listOf("Web Components", "Angular", "React", "Stencil"),
                values = mapOf(
                    ("JSX / Virtual DOM" to "Web Components") to "no",
                    ("JSX / Virtual DOM" to "Angular") to "no",
                    ("JSX / Virtual DOM" to "React") to "y︎es",
                    ("JSX / Virtual DOM" to "Stencil") to "y︎es",

                    ("TypeScript" to "Web Components") to "no",
                    ("TypeScript" to "Angular") to "y︎es",
                    ("TypeScript" to "React") to "no",
                    ("TypeScript" to "Stencil") to "y︎es",

                    ("Decorators" to "Web Components") to "no",
                    ("Decorators" to "Angular") to "y︎es",
                    ("Decorators" to "React") to "no",
                    ("Decorators" to "Stencil") to "y︎es",

                    ("Prerendering SSR" to "Web Components") to "no",
                    ("Prerendering SSR" to "Angular") to "no",
                    ("Prerendering SSR" to "React") to "no",
                    ("Prerendering SSR" to "Stencil") to "yes"
                ),
                columnFn = { col ->
                    val key = when (col) {
                        "Web Components" -> "web-components"
                        "Angular"        -> "angular"
                        "React"          -> "react"
                        "Stencil"        -> "stencil"
                        else             -> throw IllegalArgumentException("Unexpected value $col")
                    }
                    compound { figure("logos/$key.svg", col) }
                },
                valueFn = { value -> """<span class="$value">︎</span>""".raw }
            )
        }
        slide("StencilJS **works everywhere**") {
            h4("Loads polyfills on-demand")
        }
        slide("Stencil Syntax is short", setOf("header-hidden")) {
            figure("stencil/stencil-syntax.png", "Stencil Syntax is short")
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
    }
    part(partTitle = {
        h2("Lit-Elements")
        figure("logos/lit-element.png", "LitElement")
        link("https://lit-element.polymer-project.org/")
    }, id = "lit-element_part") {
        slide("TODO") {
            todo { "idea" } // TODO
        }
    }
    part("Workshop") {
        slide("TODO - work") {
            todo { "links..." } // TODO
            // timer...
        }
    }
    part("Conclusion") {
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