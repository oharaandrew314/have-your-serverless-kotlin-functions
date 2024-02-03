package dev.aohara.posts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class PostsController(@Autowired private val posts: PostsRepo) {

    @GetMapping("/posts/{postId}")
    fun getPost(@PathVariable postId: String): ResponseEntity<Post> {
        return posts.findById(postId)
            .map {  ResponseEntity.ok(it) }
            .orElseGet { ResponseEntity.notFound().build() }
    }

    @DeleteMapping("/posts/{postId}")
    fun deletePost(@PathVariable postId: String): ResponseEntity<Post> {
        return posts.findById(postId).map { post ->
            posts.deleteById(postId)
            ResponseEntity.ok(post)
        }.orElseGet { ResponseEntity.notFound().build() }
    }

    @GetMapping("/posts")
    fun listPosts(): ResponseEntity<List<Post>> {
        val results = posts.findAll().toList()
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