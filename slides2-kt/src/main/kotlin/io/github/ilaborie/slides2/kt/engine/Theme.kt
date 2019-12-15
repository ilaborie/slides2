package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.jvm.tools.Natives


data class Theme(val name: String) {
    val compiled: String by lazy {
        Natives.scssFileToCss("src/main/resources/style/$name.scss")
    }

    override fun toString(): String = name

    companion object {

        val all: Map<String, Theme> = listOf(
            "base",
            "jug-tls",
            "gdg-tls",
            "devoxxfr-19",
            "voxxed-lux-19",
            "mixit-19",
            "sunnytech-19",
            "rivieradev-19",
            "tnt"
        )
            .map { it to Theme(it) }
            .toMap()

        operator fun get(name: String): Theme =
            all[name] ?: throw IllegalStateException("Missing theme $name")

        val base = get("base")
        val jugTls = get("jug-tls")
        val gdgTls = get("gdg-tls")
        val devoxxFr19 = get("devoxxfr-19")
        val voxxedLux19 = get("voxxed-lux-19")
        val mixit19 = get("mixit-19")
        val sunnyTech19 = get("sunnytech-19")
        val rivieraDev19 = get("rivieradev-19")
        val tnt = get("tnt")

        // voxxedXXyy, ...
    }
}
