import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
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
    mavenCentral()
}


val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

val vKotlintest: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("compiler-embeddable"))
    implementation(kotlin("script-runtime"))
    implementation(kotlin("script-util"))
    implementation("com.github.ajalt:clikt:1.6.0")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:$vKotlintest")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
}

val shadowJar: ShadowJar by tasks

tasks.withType<Assemble> {
    dependsOn(shadowJar)
}