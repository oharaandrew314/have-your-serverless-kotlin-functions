plugins {
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("software.amazon.awssdk:dynamodb:2.24.0") {
        exclude("software.amazon.awssdk", "apache-client")
        exclude("software.amazon.awssdk", "netty-nio-client")
    }
    implementation("software.amazon.awssdk:url-connection-client:2.24.0")

    implementation("org.http4k:http4k-serverless-lambda-runtime:5.13.6.0")
    implementation("org.http4k:http4k-format-jackson:5.13.6.0")

    implementation("org.slf4j:slf4j-api:2.0.13")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.6")
}

application {
    mainClass = "dev.aohara.posts.PostsAppKt"
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

// docker run -v ${pwd}:/source --platform=linux/amd64 http4k/amazonlinux-java-graal-community-lambda-runtime build/libs/have_your_serverless_functions-all.jar build/cake.zip