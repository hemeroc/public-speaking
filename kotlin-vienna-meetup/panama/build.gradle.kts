import org.gradle.api.JavaVersion.VERSION_21
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("application")
    kotlin("jvm") version "1.9.22"
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = VERSION_21

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

val entrypoint = "kotlinvienna.panama.PanamaKt"

application {
    mainClass.set(entrypoint)
    applicationDefaultJvmArgs = listOf(
        "--enable-native-access=ALL-UNNAMED",
        "-Djava.library.path=${projectDir}/native",
    )
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xjsr305=strict",
        )
        allWarningsAsErrors = true
        jvmTarget = java.sourceCompatibility.majorVersion
    }
}
