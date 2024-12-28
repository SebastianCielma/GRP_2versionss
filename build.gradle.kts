plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jetbrains.compose") version "1.5.10"
    application
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation("org.xerial:sqlite-jdbc:3.40.1.0")
}

application {
    mainClass.set("classesAndMain.MainApplication") // Zmień na "classesAndMain.MainApplicationKt"
}



tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

kotlin {
    sourceSets {
        val main by getting {
            kotlin.srcDir("src/main/kotlin")
        }
    }
}