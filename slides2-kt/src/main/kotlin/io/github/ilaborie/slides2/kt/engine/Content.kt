package io.github.ilaborie.slides2.kt.engine


interface Content

interface ContainerContent : Content {
    val steps: Boolean
    val inner: List<Content>
}

interface SingleContent : ContainerContent {
    override val steps: Boolean
            get() = false
    val content: Content

    override val inner: List<Content>
        get() = listOf(content)
}


inline class Id(val id: String)