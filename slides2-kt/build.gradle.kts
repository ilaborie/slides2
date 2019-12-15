import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    application
}

group = "io.github.ilaborie.slides2"
version = "1.0-SNAPSHOT"
application {
    mainClassName = "io.github.ilaborie.slides2.kt.jvm.MainKt"
}

repositories {
    jcenter()
    mavenCentral()
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

// Versions
val vKotlintest: String by project
val vClikt: String by project
val vJackson: String by project
val vKtCoroutine: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("script-util"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$vJackson")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$vKtCoroutine")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.apply {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
    }
}

// Fat Jar
tasks.register<Jar>("fatJar") {
    archiveBaseName.set("slides2-full")
    manifest {
        attributes["Main-class"] = application.mainClassName
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it)})
    with(tasks.jar.get())
}

// Download  dependencies
tasks.register("downloadDependencies") {
    println("Download dependencies...")
    configurations.testRuntimeClasspath.get().files
}


///

//task("webComponent19", type = JavaExec::class) {
//    dependsOn("assemble")
//    classpath = sourceSets["test"].runtimeClasspath
//    main = "WebcomponentsKt"
//}

task("refactoringLoop", type = JavaExec::class) {
    dependsOn("assemble")
    classpath = sourceSets["test"].runtimeClasspath
    main = "RefactoringLoop_tntKt"
}

//task("deepDiveKotlin", type = JavaExec::class) {
//    dependsOn("assemble")
//    classpath = sourceSets["test"].runtimeClasspath
//    main = "DeepDiveKotlinKt"
//}


task("cssClockwork", type = JavaExec::class) {
    dependsOn("assemble")
    classpath = sourceSets["test"].runtimeClasspath
    main = "CssClockwork_tntKt"
}
