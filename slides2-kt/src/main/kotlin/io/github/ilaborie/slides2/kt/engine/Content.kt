package io.github.ilaborie.slides2.kt.engine


interface Content

interface ContainerContent : Content {
    val inner: List<Content>
}