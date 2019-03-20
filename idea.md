Brainstorming IDEA
===


[P1]

* Embedded Code Editor
  * Web <https://stackblitz.com>
  * Scala <https://scastie.scala-lang.org/>
  * Kotlin <https://github.com/JetBrains/kotlin-playground>, <https://jetbrains.github.io/kotlin-playground/examples/>
  * Java <https://repl.it/languages/java>
  * Rust embedded (see rustbook)
  * Monaco editor for other
* In Place CSS live code

[P2]

* Chart
  * VegaLite Chart
  * Pure HTML CSS chart <https://ffoodd.github.io/sseeeedd/graphiques.html>
* Embedded Terminal ?
* Figure Effect <https://bennettfeely.com/image-effects/>
* Marker Highlight with Houdini <https://css-houdini.rocks/highlighter-marker-annotations>

[P2]

Transpile TS/JS to ES2105 with Rust
<https://swc-project.github.io/>

[P3]

* UML with <http://plantuml.com/fr/>
* draw.io

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


### [P2] Eval code

Today: transpile TS to JS, eval are done in browser
Maybe just use <https://github.com/firecracker-microvm/firecracker>

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