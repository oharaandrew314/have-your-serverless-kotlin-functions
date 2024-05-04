plugins {
    kotlin("jvm") version "1.9.22"
    id("io.quarkus") version "3.10.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-amazon-services-bom:3.10.0"))
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:3.10.0"))

    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkiverse.amazonservices:quarkus-amazon-dynamodb-enhanced")
    runtimeOnly("software.amazon.awssdk:url-connection-client")  // quarkus gets upset if this isn't available
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}