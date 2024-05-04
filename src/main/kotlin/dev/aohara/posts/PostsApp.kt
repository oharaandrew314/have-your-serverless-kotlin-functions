package dev.aohara.posts

import org.http4k.core.Filter
import org.http4k.core.then
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.http4k.serverless.ApiGatewayV2LambdaFunction
import org.http4k.serverless.AppLoader
import org.slf4j.LoggerFactory
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient

private val logger = LoggerFactory.getLogger("root")
private val logFilter = Filter { next ->
    { request ->
        val response = next(request)
        logger.info("${request.method} ${request.uri}: ${response.status}")
        response
    }
}

class LambdaHandler : ApiGatewayV2LambdaFunction(AppLoader { envMap ->
    val posts = PostsRepo(
        dynamoDb = DynamoDbEnhancedClient.create(),
        tableName = envMap["TABLE_NAME"]!!
    )

    logFilter
        .then(postsController(posts))
})

fun main() {
    val posts = PostsRepo(
        dynamoDb = DynamoDbEnhancedClient.create(),
        tableName = System.getenv("TABLE_NAME")
    )

    logFilter
        .then(postsController(posts))
        .asServer(SunHttp(8080))
        .start()
        .also { logger.info("Server started on http://localhost:${it.port()}") }
        .block()
}