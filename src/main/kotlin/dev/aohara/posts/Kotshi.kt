package dev.aohara.posts

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.http4k.format.*
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
private object PostsJsonAdapterFactory : JsonAdapter.Factory by KotshiPostsJsonAdapterFactory

val kotshi = Moshi.Builder()
    .add(PostsJsonAdapterFactory)
    .add(ListAdapter)
    .add(MapAdapter)
    .asConfigurable()
    .withStandardMappings()
    .done()
    .let { ConfigurableMoshi(it) }