import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.dsl.raw
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.script
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.rivieraDev19
import io.github.ilaborie.slides2.kt.engine.contents.inlineFigure
import io.github.ilaborie.slides2.kt.engine.contents.speaker
import io.github.ilaborie.slides2.kt.engine.contents.table
import io.github.ilaborie.slides2.kt.engine.plugins.*
import io.github.ilaborie.slides2.kt.jvm.jvmConfig

private const val id = "deepDiveKotlin"

fun main() {
    val config = jvmConfig("presentations/deepDiveKotlin")

    val deepDiveKotlinPlugin = object : WebPlugin {
        override val name: String = "DeepDiveKotlinPlugin"
        override fun scripts(): List<Script> =
            listOf(
                script("https://cdnjs.cloudflare.com/ajax/libs/pixi.js/4.8.7/pixi.min.js"),
                script("deepDiveKotlin.js"),
                script("water.js")
            )
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
    listOf("deepDiveKotlin.js", "water.js").forEach {
        scriptDir.copyOrUpdate(it, outputDir)
    }
    // Copy extra images
    listOf("cover.jpg", "clouds.jpg").forEach {
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


    part(title = "Introduction Kotlin") {
        slide("Historique", setOf("manu", "contrast")) {
            // FIXME Igor
            file("introduction_kotlin/historique.html")
        }
        slide("Cibles", setOf("manu")) {
            figure("introduction_kotlin/java.svg", "JVM")
            figure("introduction_kotlin/android.svg", "Android")
            figure("introduction_kotlin/javascript.svg", "JavaScript")
            figure("introduction_kotlin/LLVM.svg", "LLVM")

            h4("JVM et Android")
            h4("JavaScript")
            h4("Native avec LLVM")
        }

        slide("HelloWorld.kt", setOf("code", "kotlin", "manu", "live-code"), "hw-kotlin") {
            sourceCode("introduction_kotlin/HelloWorld.kt")
            code("bash") { "kotlinc HelloWorld.kt" }
        }
        slide("kotlinc", setOf("diagram", "manu")) {
            inlineFigure("introduction_kotlin/Compile Kotlin.svg", "kotlinc")
        }
        slide("hexdump", setOf("code", "hex", "manu")) {
            sourceCode("introduction_kotlin/HelloWorldKt.class.hex")
        }
        slide("Java ByteCode", setOf("code", "bytecode", "manu")) {
            sourceCode("introduction_kotlin/HelloWorldKt.class.txt")
        }
        slide("HelloWorld-java", setOf("code", "java", "manu")) {
            sourceCode("introduction_kotlin/HelloWorldKt.java")
        }
        slide("Bilan HelloWorld.kt", setOf("bilan", "contrast", "manu")) {
            ul(steps = true) {
                html {"👮‍♂️️ Kotlin ajoute des contrôles"}
                html {"du coup on a besoin de JARs en plus"}
            }
            table(
                caption = "Taille des JAR".raw,
                rows = listOf(
                    "kotlin-stdlib",
                    "kotlin-stdlib-jdk7",
                    "kotlin-stdlib-jdk8",
                    "kotlin-reflect",
                    "lombok",
                    "spring-core",
                    "jackson-databind"
                    ),
                columns = listOf("jar","version","taille"),
                values = mapOf
                    (
                    ("kotlin-stdlib" to "jar") to "kotlin-stdlib",
                    ("kotlin-stdlib" to "version") to "1.3.31",
                    ("kotlin-stdlib" to "taille") to "1.3M",

                    ("kotlin-stdlib-jdk7" to "jar") to "kotlin-stdlib-jdk7",
                    ("kotlin-stdlib-jdk7" to "version") to "1.3.31",
                    ("kotlin-stdlib-jdk7" to "taille") to "3.1k",

                    ("kotlin-stdlib-jdk8" to "jar") to "kotlin-stdlib-jdk8",
                    ("kotlin-stdlib-jdk8" to "version") to "1.3.31",
                    ("kotlin-stdlib-jdk8" to "taille") to "13k",

                    ("kotlin-reflect" to "jar") to "kotlin-reflect",
                    ("kotlin-reflect" to "version") to "1.3.31",
                    ("kotlin-reflect" to "taille") to "2.8M",

                    ("lombok" to "jar") to "lombok",
                    ("lombok" to "version") to "1.18.6",
                    ("lombok" to "taille") to "1.7M",

                    ("spring-core" to "jar") to "spring-core",
                    ("spring-core" to "version") to "5.1.6",
                    ("spring-core" to "taille") to "1.3M"
                ),
                classes = setOf("step")
            )
            ul(steps = true) {
                html {"🏎 Performances ?"}
            }
        }
        slide("Performance HelloWorld.kt", setOf("bilan", "contrast", "igor")) {
            markdown {
                """> Ne croyez pas les benchmarks, faites les vous-même !

* <https://github.com/JetBrains/kotlin-benchmarks>
* <https://github.com/MonkeyPatchIo/kotlin-perf>

| Benchmark  |  Mode | Cnt |     Score |     Error | Units |
|------------|-------|-----|-----------|-----------|-------|
| testJava   | thrpt | 200 | 66490.271 | ± 879.996 | ops/s |
| testKotlin | thrpt | 200 | 72393.914 | ± 935.962 | ops/s |
"""
            }
        }
    }
    /// FIXME

}
