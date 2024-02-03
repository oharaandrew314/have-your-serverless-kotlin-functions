package dev.aohara.posts

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = [PostsRepo::class])
@Profile("main")
class MainConfig {

    @Bean
    @Primary
    fun dynamoDBMapperConfig(@Value("\${TABLE_NAME}") tableName: String): DynamoDBMapperConfig {
        return DynamoDBMapperConfig.builder().apply {
            tableNameOverride = DynamoDBMapperConfig.TableNameOverride(tableName)
        }.build()
    }

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder.defaultClient()
    }

    @Bean
    fun logFilter() = CommonsRequestLoggingFilter()
}
