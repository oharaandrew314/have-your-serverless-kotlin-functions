plugins {
    kotlin("jvm") version "1.9.22"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("software.amazon.awssdk:dynamodb-enhanced:2.24.0") {
        exclude("software.amazon.awssdk", "apache-client")
        exclude("software.amazon.awssdk", "netty-nio-client")
    }
    implementation("software.amazon.awssdk:url-connection-client:2.24.0")
    implementation("org.http4k:http4k-serverless-lambda:5.13.6.0")
    implementation("org.http4k:http4k-format-jackson:5.13.6.0")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.23.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}