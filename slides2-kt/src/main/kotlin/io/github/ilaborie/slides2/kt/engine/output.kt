package io.github.ilaborie.slides2.kt.engine


data class PresentationOutput(val title: String, val instances: List<PresentationOutputInstance>) {
    val json: String by lazy {

        """{ "title": "$title", "instances": ${instances.joinToString(", ", "[ ", "]") { it.json }}"""

    }
}

data class PresentationOutputInstance(val label: String, val path: String) {

    val json: String by lazy {
        """{ "label": "$label", "path": "$path" }"""
    }
}
