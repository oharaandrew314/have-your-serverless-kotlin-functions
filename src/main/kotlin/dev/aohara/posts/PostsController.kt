package dev.aohara.posts

import org.http4k.core.Method.GET
import org.http4k.core.Method.DELETE
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson
import org.http4k.lens.Path
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes
import java.util.UUID

private val postIdLens = Path.string().of("post_id")

val postLens = Jackson.autoBody<Post>().toLens()
val postListLens = Jackson.autoBody<List<Post>>().toLens()
val postDataLens = Jackson.autoBody<PostData>().toLens()

fun postsController(posts: PostsRepo) = routes(
    "/posts/$postIdLens" bind GET to { request ->
        posts[postIdLens(request)]
            ?.let { Response(OK).with(postLens of it) }
            ?: Response(NOT_FOUND)
    },
    "/posts/$postIdLens" bind DELETE to { request ->
        val postId = postIdLens(request)
        posts[postId]
            ?.also { posts.delete(postId) }
            ?.let { Response(OK).with(postLens of it) }
            ?: Response(NOT_FOUND)
    },
    "/posts" bind GET to {
        val results = posts.list()
        Response(OK).with(postListLens of results)
    },
    "/posts" bind POST to { request ->
        val data = postDataLens(request)
        val post = Post(
            id = UUID.randomUUID().toString(),
            title = data.title,
            content = data.content
        )
        posts.save(post)
        Response(OK).with(postLens of post)
    }
)