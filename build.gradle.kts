plugins {
    kotlin("jvm") version "1.9.22"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.http4k:http4k-connect-amazon-dynamodb:5.6.11.0")
    implementation("org.http4k:http4k-serverless-lambda:5.13.6.0")
    implementation("org.http4k:http4k-format-jackson:5.13.6.0")
    implementation("ch.qos.logback:logback-core:1.4.14")
    implementation("ch.qos.logback:logback-classic:1.4.14")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}