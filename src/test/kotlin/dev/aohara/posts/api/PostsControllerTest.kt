package dev.aohara.posts.api

import dev.aohara.posts.Post
import dev.aohara.posts.PostData
import dev.aohara.posts.PostsRepo
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ActiveProfiles
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class PostsControllerTest(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val repo: PostsRepo,
) {

    @Test
    fun `create post`() {
        // init
        repo += Samples.post

        // send request
        val request = RequestEntity(
            PostData(title = "test", content = "message"),
            HttpMethod.POST,
            URI.create("/authors/${Samples.AUTHOR1}/posts")
        )
        val response = restTemplate.exchange<Post>(request)

        // verify response
        response.statusCode shouldBe HttpStatus.OK
        val created = response.body.shouldNotBeNull()
        created.title shouldBe "test"
        created.content shouldBe "message"
        created.authorId shouldBe Samples.AUTHOR1

        // verify side-effect
        repo.list(Samples.AUTHOR1).shouldContainExactlyInAnyOrder(Samples.post, created)
    }

    @Test
    fun `list posts for author`() {
        // init
        repo += Samples.post
        repo += Samples.post2
        repo += Samples.post3

        // send request
        val request = RequestEntity<Unit>(
            HttpMethod.GET,
            URI.create("/authors/${Samples.AUTHOR1}/posts")
        )
        val response = restTemplate.exchange<List<Post>>(request)

        // verify response
        response.statusCode shouldBe HttpStatus.OK
        response.body.shouldNotBeNull().shouldContainExactlyInAnyOrder(
            Samples.post, Samples.post2
        )
    }

    @Test
    fun `get post`() {
        // init
        repo += Samples.post
        repo += Samples.post2

        // send request
        val request = RequestEntity<Unit>(
            HttpMethod.GET,
            URI.create("/posts/${Samples.post.id}")
        )
        val response = restTemplate.exchange<Post>(request)

        // verify response
        response.statusCode shouldBe HttpStatus.OK
        response.body shouldBe Samples.post
    }

    @Test
    fun `get missing post`() {
        // send request
        val request = RequestEntity<Unit>(
            HttpMethod.GET,
            URI.create("/posts/${Samples.post.id}")
        )
        val response = restTemplate.exchange<Post>(request)

        // verify response
        response.statusCode shouldBe HttpStatus.NOT_FOUND
        response.body shouldBe null
    }

    @Test
    fun `delete missing post`() {
        // init
        repo += Samples.post2

        // send request
        val request = RequestEntity<Unit>(
            HttpMethod.DELETE,
            URI.create("/posts/${Samples.post.id}")
        )
        val response = restTemplate.exchange<Post>(request)

        // verify response
        response.statusCode shouldBe HttpStatus.NOT_FOUND
        response.body shouldBe null

        // verify (no) side effect
        repo.list(Samples.AUTHOR1).shouldContainExactly(Samples.post2)
    }

    @Test
    fun `delete post`() {
        // init
        repo += Samples.post
        repo += Samples.post2

        // send request
        val request = RequestEntity<Unit>(
            HttpMethod.DELETE,
            URI.create("/posts/${Samples.post.id}")
        )
        val response = restTemplate.exchange<Post>(request)

        // verify response
        response.statusCode shouldBe HttpStatus.OK
        response.body shouldBe Samples.post

        // verify side effect
        repo.list(Samples.AUTHOR1).shouldContainExactly(Samples.post2)
    }
}