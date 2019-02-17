package io.github.ilaborie.slides2.kt.engine


interface Theme {
    val compiled: String

    companion object {
        data class ScssResource(val path: String) : Theme {
            override val compiled by lazy {
                "/* TODO */"
            }
        }

        val base: Theme = ScssResource("style/themes/base.scss")

        val tlsJug: Theme = ScssResource("style/themes/tls-jug.scss")

        val mixit19: Theme = ScssResource("style/themes/mixit-19.scss")

        // tlsGdg, xxxDevfestYy, frDevoxxYY, voxxed, ...
    }
}
