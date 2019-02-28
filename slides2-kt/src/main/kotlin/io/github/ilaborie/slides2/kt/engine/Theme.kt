package io.github.ilaborie.slides2.kt.engine

import io.github.ilaborie.slides2.kt.jvm.tools.ScssToCss


data class Theme(val name: String) {
    val compiled by lazy {
        ScssToCss.scssFileToCss("src/main/resources/style/$name.scss")
    }

    companion object {

        val base = Theme("base")
        val tlsJug = Theme("tls-jug")
        val devoxxFr19 = Theme("devoxxfr-19")
        val mixit19 = Theme("mixit-19")
        val sunnyTech19 = Theme("sunnytech-19")
        val rivieraDev19 = Theme("rivieradev-19")

        // tlsGdg, xxxDevfestYy, frDevoxxYY, voxxed, ...
    }
}
