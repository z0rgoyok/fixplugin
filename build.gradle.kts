plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.2.1"
}

group = "ru.fixprice"
version = "1.0.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.1")
        bundledPlugin("com.intellij.java")
    }
}

kotlin {
    jvmToolchain(17)
}

tasks {
    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("253.*")
        changeNotes.set("""
            <ul>
                <li>Initial release</li>
                <li>Generate UUID via intention action in empty string literals</li>
                <li>Supports Java, Kotlin, JSON, YAML, XML, and plain text</li>
            </ul>
        """.trimIndent())
    }

    publishPlugin {
        token.set(System.getenv("JETBRAINS_MARKETPLACE_TOKEN"))
    }

    signPlugin {
        certificateChainFile.set(file(".signing/chain.crt"))
        privateKeyFile.set(file(".signing/private.pem"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD") ?: "")
    }
}
