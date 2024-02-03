package dev.aohara.posts

import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.serverless.proxy.model.HttpApiV2ProxyRequest
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication(scanBasePackages = ["dev.aohara.posts"])
class PostsApp

class LambdaHandler : RequestHandler<HttpApiV2ProxyRequest?, AwsProxyResponse?> {
    private val handler = SpringBootLambdaContainerHandler.getHttpApiV2ProxyHandler(PostsApp::class.java, "main")

    override fun handleRequest(input: HttpApiV2ProxyRequest?, context: Context?): AwsProxyResponse? {
        return handler.proxy(input, context)
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(PostsApp::class.java)
        .profiles("main")
        .run(*args)
}