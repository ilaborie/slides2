package io.github.ilaborie.slides2.kt.engine

data class Stylesheet(val href: String, val media: String = "all") {

    val localHref: String by lazy {
        if (href.startsWith("http")) href.split("/").last() else href
    }

    fun asHtml(): String =
        """<link rel="stylesheet" href="$localHref" media="$media">"""
}