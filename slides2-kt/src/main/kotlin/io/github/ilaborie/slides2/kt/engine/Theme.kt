package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.jvm.tools.ScssToCss


data class Theme(val name: String) {
    val compiled: String by lazy {
        ScssToCss.scssFileToCss("src/main/resources/style/$name.scss")
    }

    override fun toString(): String = name

    companion object {

         val all: Map<String, Theme> = listOf(
            "base",
            "jug-tls",
            "devoxxfr-19", "mixit-19", "sunnytech-19", "rivieradev-19"
        )
            .map { it to Theme(it) }
            .toMap()

        operator fun get(name: String): Theme =
            all[name] ?: throw IllegalStateException("Missing theme $name")

        val base = get("base")
        val jugTls = get("jug-tls")
        val devoxxFr19 = get("devoxxfr-19")
        val mixit19 = get("mixit-19")
        val sunnyTech19 = get("sunnytech-19")
        val rivieraDev19 = get("rivieradev-19")

        // tlsGdg, voxxedXXyy, ...
    }
}
