package dev.aohara.posts

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface PostsRepo : CrudRepository<Post, String>

