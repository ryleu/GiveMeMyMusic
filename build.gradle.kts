plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "me.ryleu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val jdaVersion = "5.0.0-beta.5"

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion")
}

kotlin {
    jvmToolchain(8)
}