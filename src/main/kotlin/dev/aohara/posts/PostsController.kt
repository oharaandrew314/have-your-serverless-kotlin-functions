package dev.aohara.posts

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import jakarta.inject.Inject
import java.util.*

@Controller("/posts")
class PostsController(@Inject private val posts: PostsRepo) {

    @Get("/{postId}")
    fun getPost(postId: String) = posts.get(postId)

    @Delete("/{postId}")
    fun deletePost(postId: String) = posts.delete(postId)

    @Get
    fun listPosts() = posts.list()

    @io.micronaut.http.annotation.Post
    fun createPost(@Body data: PostData): Post {
        val post = Post(
            id = UUID.randomUUID().toString(),
            title = data.title,
            content = data.content
        )
        posts.save(post)
        return post
    }
}