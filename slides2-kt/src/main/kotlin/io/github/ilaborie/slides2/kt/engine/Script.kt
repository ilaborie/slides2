package io.github.ilaborie.slides2.kt.engine

data class Script(
    val src: String,
    val defer: Boolean,
    val module: Boolean,
    val async: Boolean
) {
    private val tags: String by lazy {
        var base = ""
        if (defer) {
            base += " defer"
        }
        if (async) {
            base += " async"
        }
        if (module) {
            base += " type=\"module\""
        }

        base
    }

    val localSrc: String by lazy {
        if (src.startsWith("http")) "./" + src.split("/").last() else src
    }

    fun asHtml(): String =
        """<script $tags src="$localSrc"></script>"""


    companion object {
        fun module(src: String): Script =
            Script(src = src, defer = false, module = true, async = false)

        fun async(src: String): Script =
            Script(src = src, defer = false, module = false, async = true)

        fun defer(src: String): Script =
            Script(src = src, defer = true, module = false, async = false)

        fun script(src: String): Script =
            Script(src = src, defer = false, module = false, async = false)
    }
}