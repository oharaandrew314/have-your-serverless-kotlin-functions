package dev.aohara.posts

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class PostData(
    val title: String,
    val content: String
)