Brainstorming IDEA
===


Kotlin DSL
---

### Plugin architecture

- Should add a custom Content
- Should define a specific render for a Content

Some basic plugins:

* From external HTML
* From external MD
* Link
* Quote
* Figure
* List (ul, li, def)
* paragraph
* StyledText (strong, emphasis, block, notice)
* BasicJs (allow Js Plugin)
  * JS keyboard
  * navbar
  * grid view

* Source Code
  * PrismJS <https://prismjs.com/>
  * CarbonNow <https://github.com/mixn/carbon-now-cli#readme>
  * highlight.js <https://highlightjs.org/>
* Embedded Code Editor 
    * Monaco editor
    * Web <https://stackblitz.com>
    * Scala <https://scastie.scala-lang.org/>
    * Kotlin <https://github.com/JetBrains/kotlin-playground>, <https://jetbrains.github.io/kotlin-playground/examples/>
    * Java <https://repl.it/languages/java>
* In Place CSS live code
* CanIUse Compat
* VegaLite Chart
* Embedded Terminal ?

### CLI Usage

See <https://github.com/ajalt/clikt>


### Highly configurable

### Multi-platform

### As Lib 

<https://medium.com/@bastienpaul/publish-a-kotlin-lib-with-gradle-kotlin-dsl-ec3369464e38>

Tools: Using Rust instead of Node
---

### Serve static page & Live reload

### Code to HTML (maybe use PrismJS) 

### Fetch compatibility data from caniuse

<https://github.com/seanmonstar/reqwest>

### HTML to PDF with puppeteer

<https://github.com/anowell/wkhtmltopdf-rs>

### MD to HTML

### Eval code

Today: transpile TS to JS, eval are done in browser


HTML Slides
---

* take a look at [AccesSlide](https://github.com/access42/AccesSlide) et la [démo](https://accesslide.net/)

* Check accessibility: [a11y.css](https://ffoodd.github.io/a11y.css/index.html)

* Replace transition with 

```css
.slides {
  scroll-behavior: smooth;
}
``` 

* Check code
Lighthouse: <https://web.dev/measure> or <https://github.com/GoogleChrome/lighthouse#readme>
<https://github.com/ffoodd/staats>, see also alternative


### PWA or app

<https://github.com/Polymer/pwa-starter-kit> 
<https://pwa-starter-kit.polymer-project.org/setup>
<https://developers.google.com/web/tools/workbox/>


### As WASM ?

<https://medium.com/@saschagrunert/a-web-application-completely-in-rust-6f6bdb6c4471>