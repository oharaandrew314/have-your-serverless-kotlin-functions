package dev.aohara.posts

import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Singleton
class PostsRepo(@Value("\${TABLE_NAME}") tableName: String) {
    private val table = DynamoDbEnhancedClient
        .builder()
        .dynamoDbClient(
            DynamoDbClient
                .builder()
                .httpClient(UrlConnectionHttpClient.create())
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build()
        )
        .build()
        .table(tableName, BeanTableSchema.create(Post::class.java))

    fun list(): List<Post> = table.scan().flatMap { it.items() }

    fun save(post: Post): Unit = table.putItem(post)

    fun get(id: String): Post? = table.getItem(Key.builder().partitionValue(id).build())

    fun delete(id: String): Post? = table.deleteItem(Key.builder().partitionValue(id).build())
}
