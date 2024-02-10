package dev.aohara.posts

import org.http4k.connect.amazon.dynamodb.DynamoDb
import org.http4k.connect.amazon.dynamodb.Http
import org.http4k.connect.amazon.dynamodb.model.TableName
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Configuration
@Profile("main")
class MainConfig {

    @Bean
    fun postsRepo(@Value("\${TABLE_NAME}") tableName: String) = postsRepo(
        dynamoDb = DynamoDb.Http(),
        tableName = TableName.of(tableName)
    )

    @Bean
    fun logFilter() = CommonsRequestLoggingFilter()
}
