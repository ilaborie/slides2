import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.rivieraDev19
import io.github.ilaborie.slides2.kt.engine.contents.inlineFigure
import io.github.ilaborie.slides2.kt.engine.contents.speaker
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.jvmConfig

private const val id = "deepDiveKotlin"

fun main() {
    val config = jvmConfig("presentations/deepDiveKotlin")

    val deepDiveKotlinPlugin = object :WebPlugin {
        override val name: String = "DeepDiveKotlinPlugin"
        override fun scripts(): List<Script> =
                listOf(Script.module("./deepDiveKotlin.js"))
    }

    SlideEngine
        .use(CheckContentPlugin)
        .use(TocPlugin, NavigatePlugin, GridPlugin)
        .use(PrismJsPlugin(showLines = true, languages = listOf("kotlin", "java")))
        .use(CatnipPlugin(), deepDiveKotlinPlugin)
        .run(config, deepDiveKotlin, listOf(rivieraDev19))

    val outputDir = config.output / id
    // Copy Script
    val scriptDir = config.input / "scripts"
    listOf("deepDiveKotlin.js").forEach {
        scriptDir.copyOrUpdate(it, outputDir)
    }
    // Copy extra images
    listOf("cover.png").forEach {
        config.input.copyOrUpdate(it, outputDir)
    }
}

val deepDiveKotlin = pres(
    id = id,
    extraStyle = "style",
    title = { h1 { html { "Deep Dive Kotlin :<br> du Hello World au ByteCode" } } }) {
    part("Introduction", skipHeader = true) {
        slide("Speakers", setOf("header-hidden")) {
            speaker(
                fullName = "Emmanuel Vinas",
                classes = setOf("monkeyPatch"),
                src = "speakers/emmanuel.jpg",
                info = "Expert Android & Java",
                links = mapOf(
                    "@emmanuelvinas" to "https://twitter.com/emmanuelvinas",
                    "emmanuel@monkeypatch.io" to "mailto:emmanuel@monkeypatch.io"
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
            figure("logos/monkeypatch.svg", "MonkeyPatch")
        }
        roadmap(title = "Roadmap")
    }
    part("ByteCode Java ?", "bytecode") {
        slide("javac", setOf("diagram", "igor")) {
            inlineFigure("bytecode/Compile Java.svg", "Java compiler")
        }
        slide("HelloWorld.java", setOf("code", "java", "igor", "live-code"), "hw-java") {
            sourceCode("bytecode/HelloWorld.java")
            code("bash") { "javac HelloWorld.java" }
        }
        slide("Java ByteCode binary", setOf("code", "hex", "igor", "live-code")) {
            code("bash") { "hexdump -C HelloWorld.class" }
            sourceCode("bytecode/HelloWorld.class.hex")
        }
        slide("Explorons le ByteCode", setOf("code", "bytecode", "igor", "live-code")) {
            code("bash") { "javap -c HelloWorld.class" }
            sourceCode("bytecode/HelloWorld.class.txt")
        }
        slide("À propos du ByteCode", setOf("details", "contrast", "igor", "steps"), "bytecode-details") {
            ul(steps = true, classes = setOf("bullet")) {
                html { "Environ 200 opérations possibles (maxi. 256 opscodes)" }
                markdown { "Préfix pour le type d'opérations (`i` pour entier, `d` pour double, ...)" }
                markdown { "Manipulation de la pile, des variables locales (`iconst_0`, `istore`, `iload`, ...)" }
                markdown { "Contrôle du flux des instructions (`if_icmpgt`, `goto`, ...)" }
                markdown { "Arithmétiques et conversion de type (`iadd`, `iinc`, `i2d`, ...)" }
                markdown { "Manipulation d'objets (`invokevirtual`, `invokedynamic`, ...)" }
                markdown { "Autres (`athrow`, ...)" }
            }
        }
        slide("Jouons un peu", id = "bytecode-play", styles = setOf("steps")) {
            html { "<div class=\"catnip\"></div>" }
        }
        slide("Liens", setOf("bilan", "contrast", "igor"), "bytecode-links") {
            markdown {
                """
* [Mastering Java Bytecode at the Core of the JVM](https://zeroturnaround.com/rebellabs/rebel-labs-report-mastering-java-bytecode-at-the-core-of-the-jvm/)
* [Introduction to Java Bytecode](https://mahmoudanouti.wordpress.com/2018/03/20/introduction-to-java-bytecode/)
* [The Java® Virtual Machine Specification](https://docs.oracle.com/javase/specs/jvms/se10/html/index.html)
* [The Java Virtual Machine Instruction Set](https://docs.oracle.com/javase/specs/jvms/se10/html/jvms-6.html)
* [Suivez le lapin blanc : Exploration au coeur de la JVM](https://www.youtube.com/watch?v=rB0ElXf05nU)
* [Byte Buddy](http://bytebuddy.net/#/)
* [asm](http://asm.ow2.org/)

> Soyez curieux, regardez comment ça marche avec `javap -c` !""".trimIndent()
            }
        }
    }
}
//
//private fun ContainerBuilder.litElementTitle() {
//    h2("Lit-Elements")
//    figure("logos/lit-element.svg", "LitElement")
//    link("https://lit-element.polymer-project.org/")
//}
//
//private fun ContainerBuilder.stencilTitle() {
//    h2("StencilJS")
//    inlineFigure("logos/stencil.svg", "StencilJS")
//    link("https://stenciljs.com/")
//}
//
//private fun ContainerBuilder.webComponentTitle() {
//    h2("Web Components")
//    inlineFigure("logos/web-components.svg", "Web Components")
//}
//
//private fun ContainerBuilder.mainTitle() {
//    h1("Web Components")
//    ul(classes = setOf("list-inline")) {
//        listOf(
//            inlineFigure("logos/web-components.svg", "Natif"),
//            inlineFigure("logos/stencil.svg", "StencilJS"),
//            figure("logos/lit-element.svg", "LitElement")
//        )
//    }
//}