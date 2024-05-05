package dev.aohara.posts

import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.ReturnValue

@Singleton
class PostsRepo(@Value("\${TABLE_NAME}") private val tableName: String) {
    private val client = DynamoDbClient
        .builder()
        .httpClient(UrlConnectionHttpClient.create())
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .build()

    fun list() = client.scan {
        it.tableName(tableName)
    }
        .items()
        .map { it.toPost() }

    fun save(post: Post) {
        client.putItem {
            it.tableName(tableName)
            it.item(mapOf(
                "id" to AttributeValue.fromS(post.id),
                "title" to AttributeValue.fromS(post.title),
                "content" to AttributeValue.fromS(post.content)
            ))
        }
    }

    fun get(id: String) = client.getItem {
        it.tableName(tableName)
        it.key(mapOf("id" to AttributeValue.fromS(id)))
    }.item()?.toPost()

    fun delete(id: String) = client.deleteItem {
        it.tableName(tableName)
        it.key(mapOf("id" to AttributeValue.fromS(id)))
        it.returnValues(ReturnValue.ALL_OLD)
    }.attributes()?.toPost()
}

private fun Map<String, AttributeValue>.toPost() = Post(
    id = get("id")!!.s(),
    title = get("title")!!.s(),
    content = get("content")!!.s()
)
