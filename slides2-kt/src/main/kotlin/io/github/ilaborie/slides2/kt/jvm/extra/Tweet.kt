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
import io.github.ilaborie.slides2.kt.engine.plugins.RendererPlugin
import io.github.ilaborie.slides2.kt.jvm.JvmFolder
import io.github.ilaborie.slides2.kt.utils.CachingFolder
import java.net.URL


data class Tweet(val tweetId: String) : Content {

    val content: String by lazy {
        cachingFolder[tweetId]
    }

    companion object {

        private val mapper = ObjectMapper()
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .findAndRegisterModules()

        private val cachingFolder: CachingFolder = CachingFolder(JvmFolder(".cache/tweet")) { id ->
            URL("https://api.twitter.com/1/statuses/oembed.json?=true&id=$id")
                .readText()
                .let { mapper.readValue<TweetInfo>(it) }
                .html
        }

        private data class TweetInfo(val html: String, val width: Int)

        fun ContainerBuilder.tweet(tweetId: String) {
            content.add { Tweet(tweetId) }
        }

        object TweetPlugin : RendererPlugin<Tweet> {
            override val name = "Tweet"

            override val clazz = Tweet::class.java

            override fun renderers(): List<Renderer<Tweet>> =
                listOf(TweetTextRenderer, TweetHtmlRenderer)
        }

        private object TweetHtmlRenderer : Renderer<Tweet> {
            override val mode: RenderMode = Html

            override fun render(content: Tweet): String =
                content.content
        }

        private object TweetTextRenderer : Renderer<Tweet> {
            override val mode: RenderMode = Text
            override fun render(content: Tweet): String =
                content.toString()
        }


    }
}

