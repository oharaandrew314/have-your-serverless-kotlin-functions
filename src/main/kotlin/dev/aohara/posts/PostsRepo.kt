package dev.aohara.posts

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema

@ApplicationScoped
class PostsRepo(client: DynamoDbEnhancedClient, @ConfigProperty(name = "TABLE_NAME") tableName: String) {
//class PostsRepo(@ConfigProperty(name = "TABLE_NAME") tableName: String) {

    private val table = client.table(tableName, BeanTableSchema.create(Post::class.java))

    fun list(): List<Post> = table.scan().flatMap { it.items() }

    fun save(post: Post): Unit = table.putItem(post)

    fun get(id: String): Post? = table.getItem(Key.builder().partitionValue(id).build())

    fun delete(id: String): Post? = table.deleteItem(Key.builder().partitionValue(id).build())
}