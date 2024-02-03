package dev.aohara.posts

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.filter.CommonsRequestLoggingFilter
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient

@Configuration
@Profile("main")
class MainConfig {

    @Bean
    fun postsRepo(@Value("\${TABLE_NAME}") tableName: String) = PostsRepo(
        dynamoDb = DynamoDbEnhancedClient.create(),
        tableName = tableName
    )

    @Bean
    fun logFilter() = CommonsRequestLoggingFilter()
}
