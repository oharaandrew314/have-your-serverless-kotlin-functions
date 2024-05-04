package dev.aohara.posts

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class Post(
    @get:DynamoDbPartitionKey
    var id: String? = null,

    var title: String? = null,
    var content: String? = null,
)