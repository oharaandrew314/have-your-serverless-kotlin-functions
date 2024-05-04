package dev.aohara.posts

import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.kotlin.core.json.Json
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import java.util.*

private fun Json.jsonOf(post: Post) = obj(
    "id" to post.id,
    "title" to post.title,
    "content" to post.content
)

fun Router.postsController(posts: PostsRepo) {
    // list
    route("/posts").handler { context ->
        val result = posts.list()
        context.json(
            json {
                array(result.map { jsonOf(it) })
            }
        )
    }

    // get
    route("/posts/:id").handler { context ->
        val id = context.pathParam("id")
        val post = posts.get(id)
        if (post == null) {
            context.response().setStatusCode(404)
        } else {
            context.json(
                json {
                    jsonOf(post)
                }
            )
        }
    }

    // delete
    route(HttpMethod.DELETE, "/posts/:id").handler { context ->
        val id = context.pathParam("id")
        val post = posts.delete(id)

        if (post == null) {
            context.response().setStatusCode(404)
        } else {
            context.json(
                json {
                    jsonOf(post)
                }
            )
        }
    }

    // create
    route(HttpMethod.POST, "/posts").handler { context ->
        val data = context.body().asJsonObject()
        val post = Post(
            id = UUID.randomUUID().toString(),
            title = data.getString("title"),
            content = data.getString("content")
        ).also(posts::save)

        context.json(
            json {
                jsonOf(post)
            }
        )
    }
}