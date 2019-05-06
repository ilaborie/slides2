import io.github.ilaborie.slides2.kt.SlideEngine
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.dsl.em
import io.github.ilaborie.slides2.kt.dsl.pres
import io.github.ilaborie.slides2.kt.dsl.raw
import io.github.ilaborie.slides2.kt.engine.Script
import io.github.ilaborie.slides2.kt.engine.Script.Companion.script
import io.github.ilaborie.slides2.kt.engine.Theme.Companion.rivieraDev19
import io.github.ilaborie.slides2.kt.engine.contents.*
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
        .use(TocPlugin, NavigatePlugin)
        .use(PrismJsPlugin(showLines = true, languages = listOf("kotlin", "java")))
        .use(CatnipPlugin(), deepDiveKotlinPlugin, MathJaxPlugin())
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

    // TODO list speaker
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
                markdown { "Préfixe pour le type d'opérations (`i` pour entier, `d` pour double, ...)" }
                markdown { "Manipulation de la pile, des variables locales (`iconst_0`, `istore`, `iload`, ...)" }
                markdown { "Contrôle du flux des instructions (`if_icmpgt`, `goto`, ...)" }
                markdown { "Arithmétique et conversion de type (`iadd`, `iinc`, `i2d`, ...)" }
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

    part("Introduction Kotlin") {
        slide("Historique", setOf("manu", "contrast")) {
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
                html { "👮‍♂️️ Kotlin ajoute des contrôles" }
                html { "du coup, on a besoin de JARs en plus" }
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
                columns = listOf("jar", "version", "taille"),
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
                html { "🏎 Performances ?" }
            }
        }
        slide("Performance HelloWorld.kt", setOf("bilan", "contrast", "igor")) {
            ul(steps = true) {
                markdown {
                    """> Ne croyez pas les benchmarks, faites-les vous-même !

* <https://github.com/JetBrains/kotlin-benchmarks>
* <https://github.com/MonkeyPatchIo/kotlin-perf>
"""
                }
                jmh(
                    listOf(
                        Benchmark(
                            "testJava",
                            score = 66490.271,
                            error = 879.996,
                            mode = "thrpt",
                            cnt = 200,
                            unit = "ops/s"
                        ),
                        Benchmark(
                            "testKotlin",
                            score = 72393.914,
                            error = 935.962,
                            mode = "thrpt",
                            cnt = 200,
                            unit = "ops/s"
                        )
                    )
                )
                barChart(
                    "Benchmark Hello World",
                    values = mapOf(
                        "testJava" to 66490,
                        "testKotlin" to 72393
                    ),
                    factor = { it },
                    infoFn = { "$it ops/s" },
                    mode = BarChart.Companion.BarChartCustom(
                        min = 0,
                        max = 72393,
                        low = 0,
                        high = 72393,
                        optimum = 66490
                    )
                )
            }
        }
    }

    part("Les bases", id = "basic") {
        slide("val-var.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("basic/val-var.kt")
        }
        slide("string-template.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("basic/string-template.kt")
        }
        slide("string-template.java", setOf("code", "java", "igor")) {
            sourceCode("basic/String_templatesKt.java")
        }
        slide("ByteCode de string-template", setOf("code", "bytecode", "igor")) {
            sourceCode("basic/String_templatesKt.class.txt")
        }
        slide("numeric.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("basic/numeric.kt")
        }
        slide("numeric.java", setOf("code", "java", "igor")) {
            sourceCode("basic/NumericKt.java")
        }
        slide("ByteCode de numeric", setOf("code", "bytecode", "igor")) {
            sourceCode("basic/NumericKt.class.txt")
        }
        slide("C'est simple", setOf("bilan", "contrast", "igor"), "basic-bilan") {
            ul(steps = true) {
                markdown { "Plus de `;` <sup>*</sup>" }
                html { "😍 String templating" }
                html { "😘 Plus de types primitifs (avant la compilation)" }
                html { "🧐 Inférence de type" }
                html { "🤝 On peut mélanger du code Java et Kotlin" }
            }
        }
    }

    part("null-safety") {
        slide("billion-dollar mistake".em, setOf("contrast", "manu")) {
            quote(
                author = "Tony Hoare (C.A.R. Hoare)", quote =
                "I call it my billion-dollar mistake. It was the invention of the <code>null</code> reference in 1965. [...]. This has led to innumerable errors, vulnerabilities, and system crashes, which have probably caused a billion dollars of pain and damage in the last forty years."
            )
            link(
                "https://www.infoq.com/presentations/Null-References-The-Billion-Dollar-Mistake-Tony-Hoare",
                "Null References: The Billion Dollar Mistake"
            )
        }
        slide("null-safety.kt", setOf("code", "kotlin", "manu", "live-code")) {
            sourceCode("null_safety/null-safety.kt")
        }
        slide("null-safety.java", setOf("code", "java", "manu")) {
            sourceCode("null_safety/NullSafetyKt.java")
        }
        slide("Bilan null-safety", setOf("bilan", "contrast", "manu"), id = "elvis") {
            ul(steps = true) {
                markdown { "🎸 Elvis operator: `?:`" }
                markdown { "🙌 plus de `NullPointerException`" }
                markdown { "⚠️ quand on appelle du Java" }
                link(
                    "https://kotlinlang.org/docs/reference/java-interop.html#nullability-annotations",
                    "Le compilateur Kotlin peut interpréter des annotions de nullabilité (JSR-305, Android, ...)"
                )
                markdown { "Pas de `Optional`, si nécessaire voir [Arrow](https://arrow-kt.io/)" }
            }
        }
    }

    part("Les types", id = "types") {
        slide("Types basiques", setOf("diagram", "igor")) {
            inlineFigure("types/KotlinBaseTypes.svg", "Types de base de Kotlin")
        }
        slide("Types basiques nullable", setOf("diagram", "igor")) {
            inlineFigure("types/KotlinBaseTypes_.svg", "Types de base de Kotlin nullable")
        }
        slide("todo.kt", setOf("code", "kotlin", "igor", "play")) {
            sourceCode("types/todo.kt")
        }
        slide("Hiérarchie des types", setOf("bilan", "contrast", "igor")) {
            ul(steps = true) {
                markdown { "🤝 le `TODO()` est l'ami du TDD" }
            }
        }
    }

    part("Les fonctions", id = "fonction") {
        slide("named-params.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("fonction/named.kt")
        }
        slide("named-params.java", setOf("code", "java", "igor")) {
            sourceCode("fonction/NamedKt.java")
        }
        slide("default-value.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("fonction/default-value.kt")
        }
        slide("Comment ça marche", setOf("code", "kotlin", "igor")) {
            sourceCode("fonction/quizz.kt")
            ul(steps = true) {
                markdown { "Compile `my-app` avec `lib-v1.0.0`" }
                markdown { "`java my-app.jar -cp lib-v1.0.0`" }
                markdown { "`java my-app.jar -cp lib-v1.0.1`" }
                markdown { "Résultat ?" }
            }
        }
        slide("default-value.java", setOf("code", "java", "igor")) {
            todo { "bytecode myapp" }
//            sourceCode("fonction/Default_valueKt.java")
        }
        slide("ByteCode de default-value", setOf("code", "bytecode", "igor")) {
            //            sourceCode("fonction/Default_valueKt.class.txt")
            todo { "bytecode lib" }
        }
        slide("Kotlin c'est fun !", setOf("bilan", "contrast", "igor"), id = "fun-bilan") {
            h4("✨ Conseils", setOf("step"))
            ul(steps = true) {
                html { "Toujours typer le retour de vos fonctions" }
                markdown { "(sauf si c'est évident et une surcharge comme le `toString`)" }
                html { "Kotlin est plus concis que Java => évitez de faire des fonctions trop longues" }
                ul(steps = true, classes = setOf("bullet")) {
                    markdown { "Sautez une ligne après le `=`" }
                    html { "Utilisez le passage des arguments par nom quand ça lève des ambiguïtés" }
                }
            }
            h4("📝 Note", setOf("step"))
            ul(steps = true) {
                html { "Le passage des arguments par nom, ne marche pas sur les appels de code Java" }
            }
        }
    }

    part("Les lambdas", id = "lambda") {
        slide("function.kt", setOf("code", "kotlin", "manu")) {
            sourceCode("lambda/function.kt")
        }
        slide("function.java", setOf("code", "java", "manu")) {
            sourceCode("lambda/FunctionKt.java")
        }
        slide("lambda.kt", setOf("code", "kotlin", "manu", "live-code")) {
            sourceCode("lambda/lambda.kt")
        }
        slide("lambda.java", setOf("code", "java", "manu")) {
            sourceCode("lambda/LambdaKt.java")
        }
        slide("let.kt", setOf("code", "kotlin", "manu")) {
            sourceCode("lambda/let.kt")
        }
        slide("let.java", setOf("code", "java", "manu")) {
            sourceCode("lambda/LetKt.java")
        }
        slide("Les lambdas", setOf("bilan", "contrast", "manu"), id = "lambda") {
            ul(steps = true) {
                markdown { "⚠️ pas de `return`" }
                markdown { "pensez à mettre vos lambda comme dernier argument" }
                markdown { "voir aussi les `apply`, `also`, `run` , `use`, `with`" }
                link(
                    "https://proandroiddev.com/the-tldr-on-kotlins-let-apply-also-with-and-run-functions-6253f06d152b",
                    "the tldr; on Kotlin’s let, apply, also, with and run functions"
                )
            }
        }
    }

    part("Les classes", id = "class") {
        slide("astronomy.kt", setOf("code", "kotlin", "manu", "live-code")) {
            sourceCode("class/astronomy.kt")
        }
        slide("astronomy.java", setOf("code", "java", "manu")) {
            sourceCode("class/Planet.java")
        }
        slide("Héritage en Kotlin", setOf("code", "kotlin", "igor", "live-code")) {
            sourceCode("class/inheritance.kt")
        }
        slide("Génériques", setOf("details", "contrast", "igor"), id = "generics") {
            file("class/generics.md")
        }
        slide("Sealed", setOf("code", "kotlin", "igor")) {
            sourceCode("class/json.kt")
        }
        slide("Alias en Kotlin", setOf("code", "kotlin", "igor")) {
            sourceCode("class/typealias.kt")
        }
        slide("ByteCode d'alias", setOf("code", "bytecode", "igor")) {
            sourceCode("class/TypealiasKt.class.txt")
        }
        slide("Classe, le bilan", setOf("bilan", "contrast", "manu", "igor"), id = "class-bilan") {
            ul(steps = true) {
                markdown { "🤩 `data class`" }
                markdown { "🤔 Mais pourquoi on n'a pas ça en Java ?" }
                link("https://openjdk.java.net/jeps/8222777", "JEP draft: Records and Sealed Types")
                markdown { "Une seule classe par fichier n'est pas utile" }
                markdown { "🤓 `sealed` permet de faire des types algébriques de données (Algebraic Data Type)" }
            }
        }
    }

    part("Pause") {}

    part("ByteCode Android", id = "android") {
        slide("Compilation pour Android", setOf("diagram", "manu"), id = "compile-android") {
            inlineFigure("android/Compile Android.svg", "Compile Android")
        }
        slide("Dalvik EXecutable format", setOf("code", "hex", "manu")) {
            link("https://source.android.com/devices/tech/dalvik/dex-format", "Dalvik Executable format")
            code("bash") {
                """java -jar ./scripts/lib/d8.jar --release \
                    |            --output ./target/android/dex \
                    |            ./target/android/hello.jar""".trimMargin()
            }
            sourceCode("android/classes.dex.hex")
        }

        slide("dexdump", setOf("code", "hex", "manu")) {
            code("bash") {
                """~/.android-sdk/build-tools/23.0.1/dexdump -d \
                    |      ./target/android/dex/classes.dex \
                    |      > ./target/android/dex/classes.dex.dump""".trimMargin()
            }
            sourceCode("android/classes.dex.dump")
        }
        slide("smali", setOf("code", "smali", "manu")) {
            code("bash") {
                """sh ./scripts/lib/dextools/d2j-dex2smali.sh \
                    |     ./target/android/dex/classes.dex -f \
                    |     -o ./target/android/smali""".trimMargin()
            }
            sourceCode("android/HelloWorldKt.smali")
        }
    }

    part("Autres structures", id = "structure") {
        slide("if-then-else.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("structure/if-then-else.kt")
        }
        slide("for.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("structure/for.kt")
            markdown { "Mais aussi des `while` et `do while`" }
            markdown { "et des `break`, `continue`, et label" }
        }
        slide("when.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("structure/when.kt")
        }
        slide("for-factorial.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("structure/for-factorial.kt")
        }
        slide("ByteCode factoriel avec for", setOf("code", "bytecode", "igor")) {
            sourceCode("structure/For_factorialKt.class.txt")
        }
        slide("rec-factorial.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("structure/rec-factorial.kt")
        }
        slide("ByteCode factoriel avec récursivité", setOf("code", "bytecode", "igor")) {
            sourceCode("structure/Rec_factorialKt.class.txt")
        }
        slide("Récursion non terminale", setOf("igor")) {
            asciiMath { "x! = x xx (x-1) xx ... xx 2 xx 1" }
            asciiMath { "1! = 0!  = 1" }
            ul(steps = true) {
                asciiMath { "fact(x)" }
                stack()
                asciiMath { "x xx fact(x-1)" }
                stack("`x`")
                asciiMath { "x xx (x-1) xx fact(x-2)" }
                stack("`x - 1`", "`x`")
                asciiMath { "x xx (x-1) xx (x-2) xx ..." }
                stack("...", "`x - 1`", "`x`")
                asciiMath { "x xx (x-1) xx (x-2) xx ... xx 2 xx 1" }
                stack("`1`", "`2`", "...", "`x - 1`", "`x`")
            }
        }
        slide("Récursion terminale", setOf("igor")) {
            ul(steps = true) {
                asciiMath { "fact(x) = fact(x, 1)" }
                asciiMath { "fact(x-1, x xx 1)" }
                asciiMath { "fact(x-2, x xx (x-1))" }
                asciiMath { "fact(... , x xx (x-1) xx (x-2) xx ...)" }
                asciiMath { "fact(1, x xx (x-1) xx (x-2) xx ... xx 2)" }
                markdown { "⚠️ Nécessite une optimisation par le compilateur" }
            }
        }
        slide("tailrec-factorial.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("structure/tailrec-factorial.kt")
        }
        slide(
            "ByteCode factoriel avec recursivité terminale 1/2",
            setOf("code", "bytecode", "igor")
        ) {
            sourceCode("structure/Tailrec_factorialKt.class.txt")
        }
        slide(
            "ByteCode factoriel avec recursivité terminale 2/2",
            setOf("code", "bytecode", "igor")
        ) {
            sourceCode("structure/Tailrec_factorialKt${'$'}tailRecFactorial$1.class.txt")
        }
        slide("Performances sur 10!", setOf("measure", "contrast", "igor"), id = "performances_sur_10_2") {
            barChart(
                "Benchmark Factoriel",
                values = mapOf(
                    "JavaFor" to 433372259,
                    "KotlinFor" to 374900724,
                    "JavaRec" to 71945600,
                    "KotlinRec" to 75889169,
                    "JavaTailRec" to 74708349,
                    "KotlinTailRec" to 432005904,
                    "JavaReduce" to 21560856,
                    "KotlinReduce" to 99169023
                ),
                factor = { it },
                infoFn = { "$it ops/s" },
                mode = BarChart.Companion.BarChartCustom(
                    min = 0,
                    max = 433372259,
                    low = 374900724,
                    high = 433372259,
                    optimum = 374900724
                )
            )
        }
        slide("Bilan structures", setOf("details", "contrast", "igor"), id = "bilan-structures") {

            ul(steps = true) {
                markdown { "`when` peut être utilisé avec" }
                ul(steps = true, classes = setOf("bullet")) {
                    markdown { "des constantes" }
                    markdown { "plusieurs valeurs séparées par `,`" }
                    markdown { "une expression" }
                    markdown { "avec `is` et un type (avec un 'smart cast')" }
                }
            }
            h4("✨ Tips", setOf("step"))
            ul(steps = true) {
                markdown { "privilégier les <code>when</code> si vous avez plus de 2 cas" }
                markdown { "si vous faites des fonctions récursives, faites les <code>tailrec</code>" }
            }
        }
    }

    part("Extensions de fonctions", id = "extensions_de_fonction") {
        slide("extension.kt", setOf("code", "kotlin", "live-code", "manu")) {
            sourceCode("extensions_de_fonction/extension.kt")
        }
        slide("extension.java", setOf("java", "code", "manu")) {
            sourceCode("extensions_de_fonction/ExtensionKt.java")
        }
        slide("Extension", setOf("bilan", "contrast", "manu")) {
            ul(steps = true) {
                markdown { "Permet d'enrichir les APIs Java" }
                ul(steps = true, classes = setOf("bullet")) {
                    markdown {"[Android KTX](https://developer.android.com/kotlin/ktx)"}
                    markdown { "[Spring](https://docs.spring.io/spring/docs/current/spring-framework-reference/languages.html#kotlin-spring-projects-in-kotlin)" }
                    markdown { "[RxKotlin](https://github.com/ReactiveX/RxKotlin)" }
                    markdown { "..." }
                }
                markdown { "Permet la _SoC_ (Separation of Concerns)" }
            }
        }
    }

    part("Les collections", id = "collection") {
        slide("Collections", setOf("diagram", "igor")) {
            inlineFigure("collection/Collection.svg", "Les collections")
        }
        slide("Les Maps", setOf("diagram", "igor")) {
            inlineFigure("collection/Map.svg", "Les Maps")
        }
        slide("api.kt", setOf("code", "kotlin", "igor", "live-code")) {
            sourceCode("collection/api.kt")
        }
        slide("break-immutable.kt", setOf("code", "kotlin", "igor", "play")) {
            sourceCode("collection/break-immutable.kt")
        }
        slide("sequence.kt", setOf("code", "kotlin", "manu", "live-code")) {
            sourceCode("collection/sequence.kt")
        }
        slide("Performance des séquences 1/2", setOf("measure", "contrast", "manu"), id = "performance_des_sequences") {
            jmh(
                listOf(
                    Benchmark(
                        "ApiClassic",
                        score = 44535.029,
                        error = 3550.944,
                        mode = "thrpt",
                        cnt = 200,
                        unit = "ops/s"
                    ),
                    Benchmark(
                        "ApiSequence",
                        score = 23652.238,
                        error = 1967.535,
                        mode = "thrpt",
                        cnt = 200,
                        unit = "ops/s"
                    )
                )
            )
            barChart(
                "Benchmark séquences 1",
                values = mapOf(
                    "ApiClassic" to 44535,
                    "ApiSequence" to 23652
                ),
                factor = { it },
                infoFn = { "$it ops/s" },
                mode = BarChart.Companion.BarChartCustom(
                    min = 0,
                    max = 44535,
                    low = 23652,
                    high = 44535,
                    optimum = 44535
                )
            )
        }
        slide("sequence2.kt", setOf("code", "kotlin", "manu")) {
            sourceCode("collection/sequence2.kt")
        }
        slide(
            "Performance des séquences 2/2",
            setOf("measure", "contrast", "manu"),
            id = "performance_des_sequences2"
        ) {
            jmh(
                listOf(
                    Benchmark(
                        "ApiClassicFirst",
                        score = 241752.062,
                        error = 5022.663,
                        mode = "thrpt",
                        cnt = 200,
                        unit = "ops/s"
                    ),
                    Benchmark(
                        "ApiSequenceFirst",
                        score = 3615451.391,
                        error = 454502.198,
                        mode = "thrpt",
                        cnt = 200,
                        unit = "ops/s"
                    )
                )
            )
            barChart(
                "Benchmark sequences 1",
                values = mapOf(
                    "ApiClassicFirst" to 241752,
                    "ApiSequenceFirst" to 3615451
                ),
                factor = { it },
                infoFn = { "$it ops/s" },
                mode = BarChart.Companion.BarChartCustom(
                    min = 0,
                    max = 3615451,
                    low = 241752,
                    high = 3615451,
                    optimum = 3615451
                )
            )
        }
//        slide("timed-sequence.kt", setOf("code", "play", "kotlin", "manu")) {
//            sourceCode("collection/timed-sequence.kt")
//        }
//        slide("ranges.kt", setOf("code", "kotlin", "manu")) {
//            sourceCode("collection/ranges.kt")
//        }
//        slide("ranges.java", setOf("code", "java", "manu")) {
//            sourceCode("collection/RangesKt.java")
//        }
//        slide("tuples.kt", setOf("code", "kotlin", "manu")) {
//            sourceCode("collection/tuples.kt")
//        }
//        slide("tuples.java", setOf("code", "java", "manu")) {
//            sourceCode("collection/TuplesKt.java")
//        }
        slide("Bilan collection", setOf("details", "contrast", "igor", "manu"), id = "collection") {
            ul(steps = true) {
                markdown { "💪 Super on a de l'immutabilité, des `map`, `flatMap`, `fold`, `aggregate`,..." }
                markdown { "🤨 Mais ça reste des collections Java" }
                markdown { "API standard avec `Range`, `Pair`, et `Triple`" }
                markdown { "📏 Avant d'utiliser les `Sequence`, faites des mesures" }
            }
        }
    }

    part("Les delegates", id = "delegate") {
        slide("delegate.kt", setOf("code", "kotlin", "manu", "play")) {
            sourceCode("delegate/delegate.kt")
        }
        slide("lazy.kt", setOf("code", "kotlin", "manu", "live-code")) {
            sourceCode("delegate/lazy.kt")
        }
        slide("observable.kt", setOf("code", "kotlin", "manu", "play")) {
            sourceCode("delegate/observables.kt")
        }
        slide("lateinit.kt", setOf("code", "kotlin", "manu")) {
            sourceCode("delegate/lateinit.kt")
        }
        slide("Delegate", setOf("details", "contrast", "manu")) {
            ul(steps = true, classes = setOf("bullet")) {
                markdown {
                    """`Lazy` : utile pour les propriétés qui ne sont pas systématiquement utilisées.<br>
                ⚠️ À manipuler avec précaution dans les activités Android ( avec le cycle de vie, cela peut référencer une ancienne instance)"""
                }
                markdown { "`Delegate` : Observable, Not null, ..." }
                markdown { "`lateinit` : évite les _null check_ pour les propriétés qui ne peuvent être initialisées immédiatement (ex: référence de vues sur `Activity`, `Fragment`)." }
                markdown { "* Ne peut pas être utilisé avec les types primitifs" }
            }
        }
    }

    part("Un peu plus sur les fonctions", id = "plus_sur_les_fonctions") {
        slide("inline.kt", setOf("code", "kotlin", "igor")) {
            sourceCode("plus_sur_les_fonctions/inline.kt")
        }
        slide("Logger.java", setOf("code", "java", "igor")) {
            sourceCode("plus_sur_les_fonctions/Logger.java")
        }
        slide("reified.kt", setOf("code", "kotlin", "igor", "play")) {
            sourceCode("plus_sur_les_fonctions/reified.kt")
        }
        slide("reified.java", setOf("code", "java", "igor")) {
            sourceCode("plus_sur_les_fonctions/ReifiedKt.java")
        }
        slide("Plus sur les fonctions", setOf("details", "contrast", "igor")) {
            h4(classes = setOf("step")) {
                markdown { "Cas d'utilisation du `reified`" }
            }
            ul(steps = true) {
                markdown { "Pour créer des extensions Kotlin des fonctions Java qui utilisent des `Class<T>`" }
            }
            h4(classes = setOf("step")) {
                markdown { "Cas d'utilisation du `inline`, `noinline`, `crossinline`" }
            }
            ul(steps = true) {
                markdown { "Quand on utilise `reified`" }
                markdown { "Quand on sait ce qu'on fait, [https://kotlinlang.org/docs/reference/inline-functions.html](https://kotlinlang.org/docs/reference/inline-functions.html)" }
            }
        }
    }

    part("Conclusion") {
        slide("Android", setOf("contrast", "manu")) {
            ul {
                markdown { "Faible surcharge" }
                markdown { "Support officiel par Google" }
                markdown { "[Using Project Kotlin for Android](https://docs.google.com/document/d/1ReS3ep-hjxWA8kZi0YqDbEhCqTt29hG8P44aA9W0DM8/edit)" }
                markdown { "[Kotlin Guide](https://android.github.io/kotlin-guides/)" }
                markdown { "[android-ktx](https://github.com/android/android-ktx)" }
                markdown { "[Kotlin Android Extensions](https://kotlinlang.org/docs/tutorials/android-plugin.html)" }
            }
        }
        slide("Serveur", setOf("contrast", "igor")) {
            ul {
                markdown { "Supporté officiellement depuis [Spring 5](https://projects.spring.io/spring-framework/), [Spring Boot 2](https://projects.spring.io/spring-boot/)" }
                markdown { "[SparkJava](https://sparktutorials.github.io/2017/01/28/using-spark-with-kotlin.html), [javalin](https://javalin.io/)" }
                markdown { "[Vert.x](http://vertx.io/docs/vertx-core/kotlin/)" }
                markdown { "[KTor](http://ktor.io/)" }
                markdown { "..." }
            }
        }
        slide("Web et Natif", setOf("contrast", "igor", "manu")) {
            h4("🕸 Web")
            ul {
                html { "Partager du code commun" }
                markdown { "[Use Kotlin with npm, webpack and react](https://blog.jetbrains.com/kotlin/2017/04/use-kotlin-with-npm-webpack-and-react/)" }
            }
            h4("Natif")
            ul {
                html { "Faire des applications sans JVM" }
                html { "Partager du code avec iOS" }
                html { "WebAssembly" }
            }
        }
        slide("Bilan", setOf("details", "contrast", "igor", "manu"), id = "bilan-other") {
            ul(steps = true) {
                markdown { "💎 JVM" }
                markdown { "😎 Le byte code c'est cool" }
                markdown { "🔮 Généralement, ça ne suffit pas pour prédire les performances" }
                markdown { "📏 Mesurez !" }
            }
        }
        slide("Tendance", setOf("contrast", "trends", "igor", "manu")) {
            figure("conclusion/trends.svg", "Tendance")
            link(
                "https://insights.stackoverflow.com/trends?tags=kotlin%2Cscala%2Cgroovy%2Cclojure",
                "Stackoverflow insights"
            )
        }
        slide("Kotlin vs Java", setOf("contrast", "manu", "igor"), id = "kotlin_vs_java") {
            ul(steps = true) {
                markdown { "C'est déjà mature" }
                markdown { "✊ Code plus expressif, plus sûr, plus simple" }
                markdown { "🤝 Interopérable avec Java" }
                markdown { "👍 Outillage (éditeur, gradle, maven)" }
                markdown { "👍 Ecosystème et communauté" }
                markdown { "🚀 Évolution rapide" }
                markdown { "🐣 Code multiplatform" }
                markdown { "> Kotlin réussit une belle alchimie entre pragmatisme, puissance, sûreté, accessibilité." }
            }
        }
        slide("Merci", setOf("contrast", "manu", "igor")) {
            h4("Questions ?")
        }
    }
}

private fun ContainerBuilder.stack(vararg strings: String) {
    ul(classes = setOf("stack")) {
        strings.forEach { span(it) }
    }
}