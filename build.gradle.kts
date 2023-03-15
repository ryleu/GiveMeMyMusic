plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "me.ryleu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.5")
    implementation("com.github.walkyst:lavaplayer-fork:1.4.0")
}

kotlin {
    jvmToolchain(8)
}