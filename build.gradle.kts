
plugins {
    java
    kotlin("jvm") version "1.5.10"
    `maven-publish`
}

group = "io.github.highright1234"
version = "1.0-SNAPSHOT"

val adventureVersion = rootProject.properties["adventureVersion"]

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://libraries.minecraft.net")
    maven("https://nexus.velocitypowered.com/repository/maven-public/")
}

dependencies {

    implementation("io.netty:netty-all:4.1.67.Final")

    implementation("org.slf4j:slf4j-api:1.7.30")

    implementation("org.yaml:snakeyaml:1.29")

    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.google.guava:guava:30.1.1-jre")

    implementation("com.velocitypowered:velocity-native:3.0.0")

    implementation("net.kyori:adventure-nbt:$adventureVersion")
    implementation("net.kyori:adventure-api:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-gson:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-plain:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-legacy:$adventureVersion")

    implementation("com.mojang:brigadier:1.0.18")

    implementation(kotlin("stdlib-jdk8"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}