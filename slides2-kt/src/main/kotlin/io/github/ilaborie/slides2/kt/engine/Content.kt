package io.github.ilaborie.slides2.kt.engine


interface Content {
    val steps: Boolean
        get() = false
}

interface ContainerContent : Content {
    val inner: List<Content>
}

interface SingleContent : ContainerContent {
    override val steps: Boolean
        get() = false
    val content: Content

    override val inner: List<Content>
        get() = listOf(content)

    fun first(predicate: (Content) -> Boolean): Content? =
        if (predicate(this)) this
        else inner.first { predicate(it) }

    fun any(predicate: (Content) -> Boolean): Boolean =
        first(predicate) != null
}


inline class Id(val id: String)