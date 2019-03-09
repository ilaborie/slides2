package io.github.ilaborie.slides2.kt.engine


inline class Id(val id: String)

interface Content {
    val steps: Boolean
        get() = false

    // plugContent
    fun flatten(): Sequence<Content> =
        when (this) {
            is Presentation     ->
                sequenceOf<Content>(this) +
                        title.flatten() +
                        parts.asSequence<Content>()
                            .flatMap { it.flatten() }
            is Part             ->
                sequenceOf<Content>(this) +
                        title.flatten() +
                        allSlides.asSequence<Content>()
                            .flatMap { it.flatten() }

            is Slide            ->
                sequenceOf<Content>(this) +
                        content.asSequence()
                            .flatMap { it.flatten() }
            is ContainerContent ->
                sequenceOf<Content>(this) +
                        inner.asSequence()
                            .flatMap { it.flatten() }
            else                ->
                sequenceOf(this)
        }
}

interface ContainerContent : Content {
    val inner: List<Content>
    val classes: Set<String>
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

