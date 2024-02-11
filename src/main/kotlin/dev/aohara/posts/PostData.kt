package dev.aohara.posts

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class PostData(
    val title: String,
    val content: String
)