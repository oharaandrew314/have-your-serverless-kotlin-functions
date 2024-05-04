plugins {
    kotlin("jvm") version "1.9.22"
}

repositories {
    mavenCentral()
}

private val vertxVersion = "4.5.7"

dependencies {
    implementation(platform("io.quarkus.platform:quarkus-amazon-services-bom:3.10.0"))
    implementation(platform("software.amazon.awssdk:bom:2.24.0"))
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")

    implementation("software.amazon.awssdk:dynamodb-enhanced") {
        exclude("software.amazon.awssdk", "apache-client")
        exclude("software.amazon.awssdk", "netty-nio-client")
    }
    implementation("software.amazon.awssdk:url-connection-client")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}