plugins {
    id("buildsrc.convention.kotlin-jvm")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.konsist)
}

tasks.test {
    useJUnitPlatform()
}