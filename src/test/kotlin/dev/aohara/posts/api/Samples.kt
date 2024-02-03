package dev.aohara.posts.api

import dev.aohara.posts.Post
import dev.aohara.posts.PostData

object Samples {
    const val AUTHOR1 = 1
    const val AUTHOR2 = 2

    val post = Post(
        id = "post1",
        title = "Cats Rule",
        content = "No questions taken",
        authorId = AUTHOR1
    )
    val post2 = Post(
        id = "post2",
        title = "title",
        content = "content",
        authorId = AUTHOR1
    )
    val post3 = Post(
        id = "post3",
        title = "title",
        content = "content",
        authorId = AUTHOR2
    )

    val data = PostData(
        title = "Cats Rule",
        content = "No questions taken"
    )
}