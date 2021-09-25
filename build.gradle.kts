
plugins {
    java
    kotlin("jvm") version "1.5.10"
}

group = "com.github.highright1234"
version = "1.0-SNAPSHOT"

val adventureVersion = rootProject.properties["adventureVersion"]

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("io.netty:netty-all:4.1.67.Final")

    implementation("org.slf4j:slf4j-api:1.7.30")

    implementation("org.yaml:snakeyaml:1.29")

    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.google.guava:guava:30.1.1-jre")

    implementation("org.jetbrains:annotations:16.0.2")

    implementation("net.kyori:adventure-nbt:$adventureVersion")
    implementation("net.kyori:adventure-api:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-gson:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-plain:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-legacy:$adventureVersion")

    implementation(kotlin("stdlib-jdk8"))
}