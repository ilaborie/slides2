import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

    repositories {
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:5.0.0")
    }
}

plugins {
    kotlin("jvm") version "1.3.30"
    id("com.github.johnrengelman.shadow") version "5.0.0"
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

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("script-util"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$vJackson")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.apply {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
    }
}

val shadowJar: ShadowJar by tasks

tasks.withType<Assemble> {
    dependsOn(shadowJar)
}

task("webComponent19", type = JavaExec::class) {
    dependsOn("assemble")
    classpath = sourceSets["test"].runtimeClasspath
    main = "WebcomponentsKt"
}

task("refactoringLoop", type = JavaExec::class) {
    dependsOn("assemble")
    classpath = sourceSets["test"].runtimeClasspath
    main = "RefactoringLoopKt"
}