package dev.aohara.posts

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Post(
    val id: String,
    val title: String,
    val content: String
)