package dev.aohara.posts

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest

class PostsRepo(dynamoDb: DynamoDbEnhancedClient, tableName: String) {

    private val table = dynamoDb.table(tableName, BeanTableSchema.create(DynamoPost::class.java))

    fun createTable() = table.createTable()

    operator fun get(id: String): Post? {
        return table.getItem(Key.builder().partitionValue(id).build())?.toModel()
    }

    fun list(authorId: Int): List<Post> {
        return table.index(authorIndex).query { req: QueryEnhancedRequest.Builder ->
            val key = Key.builder().partitionValue(authorId).build()
            req.queryConditional(QueryConditional.keyEqualTo(key))
        }.flatMap { it.items() }
            .map { it.toModel() }
    }

    operator fun plusAssign(post: Post) {
        table.putItem(post.toDynamo())
    }

    operator fun minusAssign(id: String) {
        table.deleteItem(Key.builder().partitionValue(id).build())
    }
}

private const val authorIndex = "authorId"

@DynamoDbBean
data class DynamoPost(
    @get:DynamoDbPartitionKey
    @get:DynamoDbSecondarySortKey(indexNames = [authorIndex])
    var id: String? = null,

    var title: String? = null,
    var content: String? = null,

    @get:DynamoDbSecondaryPartitionKey(indexNames = [authorIndex])
    var authorId: Int? = null
)

private fun Post.toDynamo() = DynamoPost(
    id = id,
    title = title,
    content = content,
    authorId = authorId
)

private fun DynamoPost.toModel() = Post(
    id = id!!,
    title = title!!,
    content = content!!,
    authorId = authorId!!
)