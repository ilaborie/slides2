package io.github.ilaborie.slides2.kt.engine


data class PresentationOutput(val title: String, val instances: List<PresentationOutputInstance>) {
    val json: String by lazy {
        """{ "title": "${title.replace('\n', ' ')}", "instances": ${instances.joinToString(
            ", ",
            "[ ",
            "]"
        ) { it.json }}}"""

    }
}

data class PresentationOutputInstance(val label: String, val path: String, val metadata: Map<String, String>) {

    val json: String by lazy {
        """{
          |  "label": "${label.replace('\n', ' ')}",
          |  "path": "$path",
          |  "metadata": ${metadata.map { (key, value) -> """"$key": "$value"""" }.joinToString(", ", "{ ", "}")}
          |}""".trimMargin()
    }
}
