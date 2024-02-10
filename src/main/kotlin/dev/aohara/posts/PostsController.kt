package dev.aohara.posts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

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
            ?.also { posts.delete(postId) }
            ?.let {  ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/posts")
    fun listPosts(): ResponseEntity<List<Post>> {
        val results = posts.primaryIndex().scan().toList()
        return ResponseEntity.ok(results)
    }

    @PostMapping("/posts")
    fun createPost(@RequestBody data: PostData): ResponseEntity<Post> {
        val post = Post(
            id = UUID.randomUUID().toString(),
            title = data.title,
            content = data.content
        )
        posts.save(post)
        return ResponseEntity.ok(post)
    }
}