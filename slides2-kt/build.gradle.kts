import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
}

group = "io.github.ilaborie.slides2"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

val vKotlintest: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("io.kotlintest:kotlintest-runner-junit5:$vKotlintest")
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
}