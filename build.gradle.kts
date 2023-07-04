plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.serialization") version "1.8.22"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")
    api("org.locationtech.jts:jts-core:1.18.1")
    api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.0")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    testImplementation(kotlin("test"))
}

group = "io.bouckaert"
version = "0.19.0"
description = "jts2geojson-kotlin"

kotlin {
    jvmToolchain(17)
}