Brainstorming IDEA
===

Kotlin DSL
---

### [P0] Plugin architecture

- Should add a custom Content
- Should define a specific render for a Content

- Could add JS 

[P0] Some basic plugins:

* From external HTML
* From external MD
* Quote
* Figure
* List (ul, li, def)
* Paragraph
* StyledText (strong, emphasis, block)
* Link
* Table
* Theme & Custom CSSmod
* Notice
* BasicJs (allow Js Plugin)
  * JS keyboard navigation
  * Navbar (part, slides, nb)
  * Welcome View

[P1]

* Source Code
  * PrismJS <https://prismjs.com/>
  * CarbonNow <https://github.com/mixn/carbon-now-cli#readme>
  * highlight.js <https://highlightjs.org/>
* Embedded Code Editor 
  * Web <https://stackblitz.com>
  * Scala <https://scastie.scala-lang.org/>
  * Kotlin <https://github.com/JetBrains/kotlin-playground>, <https://jetbrains.github.io/kotlin-playground/examples/>
  * Java <https://repl.it/languages/java>
  * Monaco editor
* In Place CSS live code
* CanIUse Compatibility

[P2]

* Chart
  * VegaLite Chart
  * Pure HTML CSS chart <https://ffoodd.github.io/sseeeedd/graphiques.html>
* Embedded Terminal ?
* Figure Effect <https://bennettfeely.com/image-effects/>
* Marker Highlight with Houdini <https://css-houdini.rocks/highlighter-marker-annotations>

### [P1] CLI Usage

See <https://github.com/ajalt/clikt>


### [P0] Highly configurable


Presentation
 - Input
 - Output Dir `<id>/<theme>.html`
 - DRAFT

### [P2] Multi-platform ?

JVM & Native at least

<https://medium.com/@alexmaisiura/kotlin-native-watchyour-files-1bd63411e6c0>
<https://kotlinlang.org/docs/tutorials/native/dynamic-libraries.html>
<https://github.com/AlexeySoshin/KotlinNativeFileWatcher>

### [P1] As Lib 

<https://medium.com/@bastienpaul/publish-a-kotlin-lib-with-gradle-kotlin-dsl-ec3369464e38>

Tools: Using Rust instead of Node
---

### [P2] Serve static page & Live reload

<https://crates.io/crates/actix-web>

See <https://github.com/actix/actix-website/blob/master/content/docs/autoreload.md> for livereload

```rust
extern crate actix_web;
use std::path::PathBuf;
use actix_web::{App, HttpRequest, Result, http::Method, fs::NamedFile};

fn index(req: &HttpRequest) -> Result<NamedFile> {
    let path: PathBuf = req.match_info().query("tail")?;
    Ok(NamedFile::open(path)?)
}

fn main() {
    App::new()
        .resource(r"/a/{tail:.*}", |r| r.method(Method::GET).f(index))
        .finish();
}
```

### [P1] Build SCSS

<https://crates.io/crates/sass-sys>

### [P2] Code to HTML 

Maybe use do it in Kotlin
Maybe use the <https://github.com/firecracker-microvm/firecracker>

### [P2] Fetch compatibility data from caniuse

Maybe do in Kotlin

<https://github.com/seanmonstar/reqwest>

```rust
extern crate reqwest;

use std::collections::HashMap;

fn main() -> Result<(), Box<std::error::Error>> {
    let resp: HashMap<String, String> = reqwest::get("https://httpbin.org/ip")?
        .json()?;
    println!("{:#?}", resp);
    Ok(())
}
```

### [P1] HTML to PDF

<https://github.com/anowell/wkhtmltopdf-rs>
<https://wkhtmltopdf.org/>

```rust
let html = r#"<html><body><div>foo</div></body></html>"#;
let mut pdf_app = PdfApplication::new().expect("Failed to init PDF application");
let mut pdfout = pdf_app.builder()
  .orientation(Orientation::Landscape)
  .margin(Size::Inches(2))
  .title("Awesome Foo")
  .build_from_html(&html)
  .expect("failed to build pdf");

pdfout.save("foo.pdf").expect("failed to save foo.pdf");
println!("generated PDF saved as: foo.pdf");
```

### [P0] MD to HTML

<https://crates.io/crates/pulldown-cmark>

```rust
use pulldown_cmark::{html, Parser};

let markdown_str = r#"
hello
=====

* alpha
* beta
"#;
let parser = Parser::new(markdown_str);

let mut html_buf = String::new();
html::push_html(&mut html_buf, parser);

assert_eq!(html_buf, r#"<h1>hello</h1>
<ul>
<li>alpha</li>
<li>beta</li>
</ul>
"#);
```

### [P2] Eval code

Today: transpile TS to JS, eval are done in browser
Maybe just use <https://github.com/firecracker-microvm/firecracker>


HTML Slides
---

`styles.css`, `theme-*.css`, `prez-*.css`

### [P1] Optimization compression

* <https://parceljs.org/>

* Maybe create a custom Asset for Slides


### [P0] Slide Engine


Maybe replace transition with  

```css
.slides {
  scroll-behavior: smooth;
}
``` 

### [P1] a11

* take a look at [AccesSlide](https://github.com/access42/AccesSlide) et la [démo](https://accesslide.net/)

* Check accessibility: [a11y.css](https://ffoodd.github.io/a11y.css/index.html)


* Check code
Lighthouse: <https://web.dev/measure> or <https://github.com/GoogleChrome/lighthouse#readme>
<https://github.com/ffoodd/staats>, see also alternative


### [P2] PWA or app

<https://github.com/Polymer/pwa-starter-kit> 
<https://pwa-starter-kit.polymer-project.org/setup>
<https://developers.google.com/web/tools/workbox/>


### [P2] As WASM ?

<https://medium.com/@saschagrunert/a-web-application-completely-in-rust-6f6bdb6c4471>