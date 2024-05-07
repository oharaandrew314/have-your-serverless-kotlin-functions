package dev.aohara.posts

import org.http4k.cloudnative.env.Environment
import org.http4k.connect.amazon.dynamodb.DynamoDb
import org.http4k.connect.amazon.dynamodb.Http
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.http4k.serverless.ApiGatewayV2LambdaFunction
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("root")
private val logFilter = Filter { next ->
    { request ->
        val response = next(request)
        logger.info("${request.method} ${request.uri}: ${response.status}")
        response
    }
}

fun createApp(envMap: Map<String, String>): HttpHandler {
    val dynamo = DynamoDb.Http(Environment.from(envMap))

    val posts = postsRepo(
        dynamoDb = dynamo,
        tableName = envMap["TABLE_NAME"]!!
    )

    return logFilter.then(postsController(posts))
}

class LambdaHandler : ApiGatewayV2LambdaFunction(::createApp)

fun main() {
    val app = createApp(System.getenv())

    logFilter
        .then(app)
        .asServer(SunHttp(8080))
        .start()
        .also { logger.info("Server started on http://localhost:${it.port()}") }
        .block()
}