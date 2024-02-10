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
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
    implementation("com.amazonaws.serverless:aws-serverless-java-container-springboot3:2.0.0")
    implementation("org.http4k:http4k-connect-amazon-dynamodb:5.6.11.0")

    testImplementation("org.springframework.boot:spring-boot-starter-tomcat")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}