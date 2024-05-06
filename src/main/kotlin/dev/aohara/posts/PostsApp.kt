package dev.aohara.posts

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.serverless.*
import org.slf4j.LoggerFactory
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

private val logger = LoggerFactory.getLogger("root")
private val logFilter = Filter { next ->
    { request ->
        val response = next(request)
        logger.info("${request.method} ${request.uri}: ${response.status}")
        response
    }
}

fun createApp(envMap: Map<String, String>): HttpHandler {
    val dynamo = DynamoDbClient
        .builder()
        .httpClient(UrlConnectionHttpClient.create())
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .build()

    val posts = PostsRepo(
        client = dynamo,
        tableName = envMap["TABLE_NAME"]!!
    )

    return logFilter.then(postsController(posts))
}

fun main() {
    ApiGatewayV2FnLoader(::createApp).asServer(AwsLambdaRuntime()).start()
}