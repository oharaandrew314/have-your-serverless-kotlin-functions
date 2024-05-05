plugins {
    kotlin("jvm") version "1.9.22"
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

repositories {
    mavenCentral()
}

application {
    mainClass = "dev.aohara.posts.PostsAppKt"
}

dependencies {
    implementation("org.http4k:http4k-connect-amazon-dynamodb:5.6.11.0")
    implementation("org.http4k:http4k-serverless-lambda-runtime:5.13.6.0")
    implementation("org.slf4j:slf4j-simple:2.0.11")
    implementation("se.ansman.kotshi:api:2.15.0")
    ksp("se.ansman.kotshi:compiler:2.15.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

// docker run -v ${pwd}:/source --platform=linux/amd64 http4k/amazonlinux-java-graal-community-lambda-runtime build/libs/have_your_serverless_functions-all.jar build/cake.zip