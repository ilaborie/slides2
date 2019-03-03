import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

    repositories {
        jcenter()
    }
    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:5.0.0")
    }
}

plugins {
    kotlin("jvm") version "1.3.21"
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
    implementation(kotlin("compiler-embeddable"))
    implementation(kotlin("script-runtime"))
    implementation(kotlin("script-util"))
    implementation("com.github.ajalt:clikt:$vClikt")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$vJackson")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:$vKotlintest")
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