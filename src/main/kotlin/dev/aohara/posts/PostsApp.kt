package dev.aohara.posts

import io.micronaut.function.aws.proxy.payload2.APIGatewayV2HTTPEventFunction
import io.micronaut.runtime.Micronaut

class LambdaHandler : APIGatewayV2HTTPEventFunction()

fun main() {
    Micronaut.run()
}