package dev.aohara.posts

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Post(
    val id: String,
    val title: String,
    val content: String
)