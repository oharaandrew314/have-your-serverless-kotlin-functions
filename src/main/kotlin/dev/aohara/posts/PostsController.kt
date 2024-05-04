package dev.aohara.posts

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import java.util.*

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PostsController(private val repo: PostsRepo) {

    @GET
    fun list() = repo.list()

    @GET
    @Path("{id}")
    fun get(id: String) = repo.get(id)

    @DELETE
    @Path("{id}")
    fun delete(id: String) = repo.delete(id)

    @POST
    fun create(data: PostData) = Post(
        id = UUID.randomUUID().toString(),
        title = data.title,
        content = data.content
    ).also(repo::save)
}