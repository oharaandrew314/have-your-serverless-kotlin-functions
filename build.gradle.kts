import io.micronaut.gradle.MicronautRuntime

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.micronaut.application") version "4.3.8"
    id("io.micronaut.aot") version "4.3.8"
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
}

repositories {
    mavenCentral()
}

graalvmNative.toolchainDetection = false
micronaut {
    version = "4.4.2"
    runtime = MicronautRuntime.LAMBDA_PROVIDED
    processing {
        incremental = true
        annotations("dev.aohara.posts.*")
    }
    aot {
        optimizeClassLoading = true
        optimizeServiceLoading = true
        precomputeOperations = true
        convertYamlToJava = true
        cacheEnvironment = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

dependencies {
    ksp("io.micronaut.serde:micronaut-serde-processor")

    implementation("software.amazon.awssdk:dynamodb:2.24.0") {
        exclude("software.amazon.awssdk", "apache-client")
        exclude("software.amazon.awssdk", "netty-nio-client")
    }
    implementation("software.amazon.awssdk:url-connection-client:2.24.0")

    implementation("io.micronaut.aws:micronaut-function-aws")
    implementation("io.micronaut.aws:micronaut-function-aws-custom-runtime")
    implementation("io.micronaut.aws:micronaut-aws-lambda-events-serde")
    implementation("io.micronaut.serde:micronaut-serde-jackson")

    runtimeOnly("io.micronaut:micronaut-http-client-jdk")
    runtimeOnly("io.micronaut.kotlin:micronaut-kotlin-runtime")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    testRuntimeOnly("io.micronaut:micronaut-http-server-netty")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    baseImage = "amazonlinux:2"
    jdkVersion = "21"
    args(
        "-XX:MaximumHeapSizePercent=80",
        "-Dio.netty.allocator.numDirectArenas=0",
        "-Dio.netty.noPreferDirect=true"
    )
}