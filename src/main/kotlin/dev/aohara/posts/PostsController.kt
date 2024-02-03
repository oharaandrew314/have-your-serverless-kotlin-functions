package dev.aohara.posts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
class PostsController(@Autowired private val posts: PostsRepo) {

    @GetMapping("/posts/{postId}")
    fun getPost(@PathVariable postId: String): ResponseEntity<Post> {
        return posts[postId]
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/posts/{postId}")
    fun deletePost(@PathVariable postId: String): ResponseEntity<Post> {
        return posts[postId]
            ?.also { posts -= postId }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/authors/{authorId}/posts")
    fun listPosts(@PathVariable authorId: Int): ResponseEntity<List<Post>> {
        val results = posts.list(authorId)
        return ResponseEntity.ok(results)
    }

    @PostMapping("/authors/{authorId}/posts")
    fun createPost(@PathVariable authorId: Int, @RequestBody data: PostData): ResponseEntity<Post> {
        val post = Post(
            id = UUID.randomUUID().toString(),
            authorId = authorId,
            title = data.title,
            content = data.content
        )
        posts += post
        return ResponseEntity.ok(post)
    }
}