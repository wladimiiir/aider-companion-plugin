plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "me.wladimiiir.plugins"
version = "0.1.2"

repositories {
    mavenCentral()
}

val ktorVersion = "2.3.9"
val slf4jVersion = "1.7.36"

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion") {
        exclude(group = "org.slf4j")
    }
    implementation("io.ktor:ktor-server-netty:$ktorVersion") {
        exclude(group = "org.slf4j")
    }
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion") {
        exclude(group = "org.slf4j")
    }
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion") {
        exclude(group = "org.slf4j")
    }
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.6")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
