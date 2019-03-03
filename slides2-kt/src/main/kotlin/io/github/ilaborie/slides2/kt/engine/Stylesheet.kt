package io.github.ilaborie.slides2.kt.engine

data class Stylesheet(val href: String, val media: String = "all") {

    fun asHtml(): String =
        """<link rel="stylesheet" href="$href" media="$media">"""
}