package io.github.ilaborie.slides2.kt.engine

data class Script(
    val src: String,
    val defer: Boolean = true,
    val module: Boolean = true,
    val async: Boolean = false
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

    fun asHtml(): String =
        """<script $tags src="$src"></script>"""
}