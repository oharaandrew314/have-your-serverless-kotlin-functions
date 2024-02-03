package dev.aohara.posts.api

import dev.aohara.posts.PostsRepo
import org.http4k.aws.AwsSdkClient
import org.http4k.connect.amazon.dynamodb.FakeDynamoDb
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Configuration
@Profile("test")
class TestConfig {

    private val dynamoDb = DynamoDbEnhancedClient.builder()
        .dynamoDbClient(
            DynamoDbClient.builder()
                .httpClient(AwsSdkClient(FakeDynamoDb()))
                .credentialsProvider { AwsBasicCredentials.create("id", "secret") }
                .region(Region.CA_CENTRAL_1)
                .build()
        )
        .build()

    @Bean
    @Primary
    fun postsRepo() = PostsRepo(dynamoDb, "posts")
        .also { it.createTable() }
}