plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    implementation("io.javalin:javalin:6.1.3")
    implementation("org.slf4j:slf4j-simple:2.0.10")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    implementation("io.javalin.community.ssl:ssl-plugin:6.1.3")

    implementation("com.google.code.gson:gson:2.8.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}