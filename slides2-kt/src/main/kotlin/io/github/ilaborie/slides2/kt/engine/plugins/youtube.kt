package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder


fun ContainerBuilder.youtube(videoId: String) {
    html {
        """
        <div class="video-wrapper" data-streaming="youtube" data-videoid="$videoId" >
            <i class="icon-video-play-button shortcode"></i>
            <div style="background-image:url(//img.youtube.com/vi/$videoId/0.jpg)" alt="YouTube Thumbnail" class="video-thumbnail"></div>
        </div>""".trimIndent()
    }
}


