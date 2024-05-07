plugins {
    kotlin("jvm") version "1.9.22"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.http4k:http4k-connect-amazon-dynamodb:5.6.11.0")
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