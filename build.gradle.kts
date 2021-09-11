plugins {
    java
}

group = "com.github.highright1234"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url = "https://libraries.minecraft.net" )
}

val adventureVersion = project.properties["adventureVersion"]
val hephaistos_version = project.properties["hephaistos_version"]

dependencies {
    implementation("io.netty:netty-all:4.1.67.Final")

    implementation("org.slf4j:slf4j-api:1.7.30")

    implementation("com.mojang:brigadier:1.0.18")

    implementation("org.yaml:snakeyaml:1.29")

    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.google.guava:guava:30.1.1-jre")

    implementation("org.jetbrains:annotations:16.0.2")

    implementation("com.github.jglrxavpok:Hephaistos:${hephaistos_version}:gson")
    implementation("com.github.jglrxavpok:Hephaistos:${hephaistos_version}") {
        capabilities {
            requireCapability("org.jglrxavpok.nbt:Hephaistos-gson")
        }
    }

    implementation("net.kyori:adventure-api:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-gson:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-plain:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-legacy:$adventureVersion")

    compileOnly("org.projectlombok:lombok:1.18.12")
    annotationProcessor("org.projectlombok:lombok:1.18.12")
}