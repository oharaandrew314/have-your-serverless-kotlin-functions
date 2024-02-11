import io.micronaut.gradle.MicronautRuntime

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.micronaut.minimal.library") version "4.3.2"
    id("io.micronaut.aot") version "4.3.2"
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
}

repositories {
    mavenCentral()
}

micronaut {
    version = "4.0.2"
    runtime = MicronautRuntime.LAMBDA_JAVA
    processing {
        incremental = false
        annotations("dev.aohara.posts.*")
    }
    aot {
        optimizeClassLoading = true
        optimizeServiceLoading = true
        precomputeOperations = true
    }
}

dependencies {
    ksp("io.micronaut.serde:micronaut-serde-processor")

    implementation("software.amazon.awssdk:dynamodb-enhanced:2.24.0")
    implementation("io.micronaut.aws:micronaut-function-aws-api-proxy")
    implementation("io.micronaut.aws:micronaut-aws-lambda-events-serde")
    implementation("io.micronaut.serde:micronaut-serde-jackson")

    runtimeOnly("io.micronaut.kotlin:micronaut-kotlin-runtime")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    testRuntimeOnly("io.micronaut:micronaut-http-server-netty")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}