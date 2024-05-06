package dev.aohara.posts

import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.ReturnValue

class PostsRepo(private val client: DynamoDbClient, private val tableName: String) {

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