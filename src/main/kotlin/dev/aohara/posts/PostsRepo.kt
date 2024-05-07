package dev.aohara.posts

import org.http4k.connect.amazon.dynamodb.DynamoDb
import org.http4k.connect.amazon.dynamodb.mapper.DynamoDbTableMapper
import org.http4k.connect.amazon.dynamodb.mapper.tableMapper
import org.http4k.connect.amazon.dynamodb.model.Attribute
import org.http4k.connect.amazon.dynamodb.model.TableName

typealias PostsRepo = DynamoDbTableMapper<Post, String, Unit>

fun postsRepo(dynamoDb: DynamoDb, tableName: String): PostsRepo = dynamoDb.tableMapper(
    tableName = TableName.of(tableName),
    hashKeyAttribute = Attribute.string().required("id"),
    autoMarshalling = postsJson
)
