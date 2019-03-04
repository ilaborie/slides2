package io.github.ilaborie.slides2.kt.jvm.extra

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.Renderer
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Html
import io.github.ilaborie.slides2.kt.engine.Renderer.Companion.RenderMode.Text
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.utils.CachingFolder
import java.net.URL

data class TweetInfo(val html: String, val width: Int)

data class Tweet(val tweetId: String) : Content {

    private val mapper = ObjectMapper()
        .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        .findAndRegisterModules()

    private val cachingFolder: CachingFolder = CachingFolder(JvmFolder(".cache/tweet")) { id ->
        URL("https://api.twitter.com/1/statuses/oembed.json?=true&id=$tweetId")
            .readText()
            .let { mapper.readValue<TweetInfo>(it) }
            .html
    }

    val content: String by lazy {
        cachingFolder[tweetId]
    }
}

fun ContainerBuilder.tweet(tweetId: String) {
    content.add { Tweet(tweetId) }
}

object TweetHtmlRenderer : Renderer<Tweet> {
    override val mode: RenderMode = Html

    override fun render(content: Tweet): String =
        content.content
}

object TweetTextRenderer : Renderer<Tweet> {
    override val mode: RenderMode = Text
    override fun render(content: Tweet): String =
        content.toString()
}

