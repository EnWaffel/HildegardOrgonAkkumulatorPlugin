plugins {
    java
    alias(libs.plugins.lavalink)
    id("com.github.johnrengelman.shadow") version "8.0.0+"
}

group = "de.enwaffel"
version = "0.1.0"

lavalinkPlugin {
    name = "HildegardOrgonAkkumulatorPlugin"
    apiVersion = libs.versions.lavalink.api
    serverVersion = libs.versions.lavalink.server
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}

dependencies {
    implementation("org.luaj:luaj-jse:3.0.1")
}