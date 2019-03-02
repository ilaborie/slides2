import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.contents.p
import io.github.ilaborie.slides2.kt.engine.contents.raw

// https://shprink.github.io/talks/2018/web_component_native_vs_stenciljs


val webComponents = pres(
    title = "Web Components : du natif à Stencil.js ou lit-element", // TODO
    id = "webComponents-19",
    extraStyle = "style"
) {
    part("Introduction") {
        slide("Speakers") {
            file("speakers/julien.html")
            file("speakers/igor.html")
            file("logos/toptal.svg")
            file("logos/monkeypatch.svg")
        }
        roadmap("Roadmap")
    }
    part("I want to build a Web app in 2019") {
        slide("Framework") {
            p { "Let's start by picking up a Framework" }
            ul(steps = true) {
                figure("logos/react.svg", "React")
                figure("logos/vuejs.svg", "VueJs")
                figure("logos/angular.svg", "Angular")
            }
        }
        slide("Style", styles = setOf("two-columns")) {
            p { "Now let's select how to write our style" }
            ul(steps = true) {
                html { "CSS" }
                html { "Sass/Scss" }
                html { "Less" }
                html { "Stylus" }
                html { "CSS-in-JS" }
                html { "PostCSS" }
                html { "NextCSS" }
            }
        }
        slide("Transpile") {
            p { "Now let's transpile our code" }
            ul(steps = true) {
                html { "Webpack" }
                html { "ParcelJs" }
                html { "RollupJs" }
                html { "Bazel" }
            }
        }
        slide("Not Easy") {
            p { "Developing an app in JS in not easy anymore..." }
        }
        slide("Moving too fast") {
            p { "The industry is moving too fast..." }
            // TODO tweet
            html {
                """<blockquote class="twitter-tweet" data-lang="fr">
               |  <p lang="en" dir="ltr">I think I&#39;ve had milk last longer than some JavaScript frameworks.</p>
               |  &mdash; I Am Devloper (@iamdevloper)
               |  <a href="https://twitter.com/iamdevloper/status/540481335362875392?ref_src=twsrc%5Etfw">4 décembre 2014</a>
               |</blockquote>
               |<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>"""
            }
        }
        slide("Interoperability") {
            p { "...Interoperability is not available out of the box..." }
            ul {
                figure("logos/react.svg", "React")
                figure("logos/vuejs.svg", "VueJs")
                figure("logos/angular.svg", "Angular")
            }
        }
        slide("Reinventing") {
            p { "...And we keep reinventing the wheel!" }
            ul(steps = true) {
                (1..10).forEach {
                    figure("img/material/material-design-$it.png", "Material $it")
                }
            }
        }
        slide("Solution") {
            markdown {
                """All this complexity is coming to an end with **Web Components**"""
            }
        }
        slide("Size matters (Gzipped)") {
            ul(steps = true) {
                barChart(
                    "Size matters (Gzipped)",
                    mapOf(
                        "Angular" to 59.0,
                        "Stencil" to 11.0,
                        "Native" to 2.5
                    ),
                    unit = "kb"
                )
                html { "Native is 23 times smaller than Angular" }
                html { "Stencil is 5 times smaller than Angular" }
            }
        }
        slide("Time matters (FMP 3G \uD83D\uDCF1 in ms)") {
            ul(steps = true) {
                barChart(
                    "Time matters (FMP 3G \uD83D\uDCF1 in ms)", mapOf(
                        "Angular" to 3000.0,
                        "Stencil" to 1070.0,
                        "Native" to 1030.0,
                        "Stencil Pre Rendered" to 980.0
                    ), unit = "ms"
                )
                html { "Native & Stencil 3 times faster than Angular" }
            }
        }
    }
    part("Web Components") {
        // TODO Logo
        slide("History") {
            ul {
                markdown { "Specs from **World Wide Web Consortium** (W3C)" }
                markdown { "First draft in **2012**" }
            }
        }
        slide("Components") {
            ul(steps = true) {
                figure("img/webcomponents/custom-elements.svg", "Custom Elements")
                figure("img/webcomponents/shadow-DOM.svg", "Shadow DOM")
                figure("img/webcomponents/HTML-templates.svg", "HTML templates")
                figure("img/webcomponents/HTML-imports.svg", "HTML imports")
            }
        }
        slide("Custom Elements") {
            figure("img/webcomponents/custom-elements.svg", "Custom Elements")
            quote { "Custom Elements is a capability for creating your own custom HTML elements with its own methods and properties".p }
        }
        slide("Shadow DOM") {
            figure("img/webcomponents/shadow-DOM.svg", "Shadow DOM")
            quote { "Shadow DOM provides encapsulation for DOM and CSS".p }
        }
        slide("HTML templates") {
            figure("img/webcomponents/HTML-templates.svg", "HTML templates")
            quote { "Give the ability to create reusable piece of HTML that can be used at runtime".p }
        }
        slide("Browser support") {
            file("img/caniuse/browser-support.html")
        }
        slide("Polyfill support") {
            file("img/caniuse/polyfill-support.html")
            link("https://github.com/webcomponents/webcomponentsjs")
        }
        slide("Framework Interoperability") {
            file("img/caniuse/frameworks-support.html")
            link("https://custom-elements-everywhere.com/")
        }
    }
    part("StencilJS") {
        // TODO Logo + Link
        slide("What") {
            ul(steps = true) {
                markdown { "**Open Source** project" }
                markdown { "Created by the **Ionic Team** in 2017" }
                markdown { "3.9k ⭐️ on github" }
                markdown { "3.9k ⭐️ on github" }
            }
        }
        slide("Not a framework") {
            markdown { "StencilJS is **not another framework**" }
        }
        slide("Compiler") {
            markdown { "StencilJS is a **compiler** that generates **web components**" }
        }
        slide("StencilJS is a **set** of **great tools**") {
            file("stencil/stencil-tools.html")
        }
        slide("StencilJS **works everywhere**") {
            title(4) { "Loads polyfills on-demand".raw }
        }
        slide("Stencil Syntax is short") {
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
    part("Lit-Elements") {
        // TODO
    }
    part("Workshop") {
        // TODO
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
        slide("End") { p { "Thanks" } }
    }
}