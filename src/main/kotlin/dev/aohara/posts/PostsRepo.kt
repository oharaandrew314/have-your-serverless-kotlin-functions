package dev.aohara.posts

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema

class PostsRepo(dynamoDb: DynamoDbEnhancedClient, tableName: String) {

    private val table = dynamoDb.table(tableName, BeanTableSchema.create(Post::class.java))

    fun list(): List<Post> = table.scan().flatMap { it.items() }

    fun save(post: Post): Unit = table.putItem(post)

    operator fun get(id: String): Post? = table.getItem(Key.builder().partitionValue(id).build())

    fun delete(id: String): Post? = table.deleteItem(Key.builder().partitionValue(id).build())
}

