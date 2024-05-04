package dev.aohara.posts

import io.vertx.core.AbstractVerticle
import io.vertx.core.Launcher
import io.vertx.ext.web.Router
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

class PostsApp: AbstractVerticle() {
    override fun start() {
        val router = Router.router(vertx)

        val posts = DynamoDbClient.builder()
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
            .httpClient(UrlConnectionHttpClient.create())
            .build()
            .let { DynamoDbEnhancedClient.builder().dynamoDbClient(it).build() }
            .let { PostsRepo(it, System.getenv("TABLE_NAME")) }

        router.postsController(posts)

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080)
            .onSuccess { server ->
                println("HTTP server started on port " + server.actualPort())
            }
    }
}

fun main(args: Array<String>) {
    Launcher.main(arrayOf("run", "dev.aohara.posts.PostsApp") + args)
}