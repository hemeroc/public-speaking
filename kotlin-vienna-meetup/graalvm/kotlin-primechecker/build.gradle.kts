import org.gradle.api.file.DuplicatesStrategy.EXCLUDE
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("application")
    id("org.jetbrains.kotlin.jvm") version "1.7.21"
    id("org.graalvm.buildtools.native") version "0.9.18"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

val entrypoint = "kotlinvienna.PrimeCheckerKt"

application {
    mainClass.set(entrypoint)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        allWarningsAsErrors = true
        jvmTarget = "11"
    }
}

tasks.withType<Jar>() {
    manifest.attributes["Main-Class"] = entrypoint
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = EXCLUDE
}

tasks.withType<Test> {
    useJUnitPlatform()
}