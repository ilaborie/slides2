import io.github.ilaborie.slides2.kt.dsl.pres

pres("Test") {
    part("A part") {
        slide("A slide") { p("lorem ipsum") }
        roadmap("Roadmap")
    }
    part("Another part") {
        slide("Slide 1") { p("lorem ipsum") }
        slide("Slide 2") { p("lorem ipsum") }
        slide("Slide 3") { p("lorem ipsum") }
        slide("Slide 4") { p("lorem ipsum") }
        slide("Slide 5") { p("lorem ipsum") }
    }
    part("Last part") {
        slide("Slide 1") { p("lorem ipsum") }
        slide("End") { p("lorem ipsum") }
    }
}