plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.23.16"))
    implementation(platform("org.http4k:http4k-connect-bom:5.6.9.0"))
    implementation(platform("io.kotest:kotest-bom:5.8.0"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("software.amazon.awssdk:dynamodb-enhanced")
    implementation("com.amazonaws.serverless:aws-serverless-java-container-springboot3:2.0.0-M2")

    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.http4k:http4k-connect-amazon-dynamodb-fake")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}