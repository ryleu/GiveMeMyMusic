plugins {
    kotlin("jvm") version "1.8.0"
}

group = "me.ryleu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}