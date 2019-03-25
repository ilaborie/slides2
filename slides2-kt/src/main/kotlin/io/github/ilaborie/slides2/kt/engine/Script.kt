package io.github.ilaborie.slides2.kt.engine

interface Script {
    fun asHtml(): String
    val localSrc: String?
    val module: Boolean

    companion object {
        const val cloudfare = "https://cdnjs.cloudflare.com/ajax/libs"

        fun unpkg(npmPackage: String, version: String? = null, path: String?): String =
            "https://unpkg.com/$npmPackage" +
                    (version?.let { "@$version" } ?: "") +
                    (path?.let { if (it.startsWith("/")) it else "/$it" } ?: "")

        fun module(src: String, extraTags: List<String> = emptyList()): Script =
            BaseScript(src = src, defer = false, module = true, async = false, extraTags = extraTags)

        fun async(src: String, extraTags: List<String> = emptyList()): Script =
            BaseScript(src = src, defer = false, module = false, async = true, extraTags = extraTags)

        fun defer(src: String, extraTags: List<String> = emptyList()): Script =
            BaseScript(src = src, defer = true, module = false, async = false, extraTags = extraTags)

        fun script(src: String, extraTags: List<String> = emptyList()): Script =
            BaseScript(src = src, defer = false, module = false, async = false, extraTags = extraTags)

        fun raw(type: String, module: Boolean = false, block: () -> String): Script =
            RawScript(type, block(), module)

        private data class RawScript(
            val type: String,
            val content: String,
            override val module: Boolean
        ) : Script {
            override fun asHtml(): String =
                """<script type="$type">
                  |$content
                  |</script>""".trimMargin()

            override val localSrc: String? = null
        }

        data class BaseScript(
            val src: String,
            val defer: Boolean,
            override val module: Boolean,
            val async: Boolean,
            val cacheLocal: Boolean = true,
            val extraTags: List<String> = emptyList()
        ) : Script {
            private val tags: String by lazy {
                val base = mutableListOf<String>()

                if (defer) {
                    base += "defer"
                }
                if (async) {
                    base += "async"
                }
                if (module) {
                    base += "type=\"module\""
                }
                base += extraTags

                base.joinToString(" ")
            }

            override val localSrc: String by lazy {
                when {
                    !cacheLocal            -> src
                    src.startsWith("http") -> src.split("/").last()
                    else                   -> src
                }
            }

            override fun asHtml(): String =
                """<script $tags src="$localSrc"></script>"""
        }
    }
}

