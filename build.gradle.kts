plugins {
    kotlin("jvm") version "1.9.22"
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.http4k:http4k-connect-amazon-dynamodb:5.6.11.0")
    implementation("org.http4k:http4k-serverless-lambda:5.13.6.0")
    implementation("ch.qos.logback:logback-core:1.4.14")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("se.ansman.kotshi:api:2.15.0")
    ksp("se.ansman.kotshi:compiler:2.15.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}