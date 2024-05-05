package dev.aohara.posts

import org.http4k.client.Java8HttpClient
import org.http4k.connect.amazon.dynamodb.DynamoDb
import org.http4k.connect.amazon.dynamodb.Http
import org.http4k.connect.amazon.dynamodb.model.TableName
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.serverless.*
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
    val posts = postsRepo(
        dynamoDb = DynamoDb.Http(http = Java8HttpClient()),
        tableName = TableName.of(envMap["TABLE_NAME"]!!)
    )

    return logFilter.then(postsController(posts))
}

fun main() {
    ApiGatewayV2FnLoader(::createApp).asServer(AwsLambdaRuntime()).start()
}