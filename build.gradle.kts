plugins {
    id("com.android.application") version "8.1.0-beta01" apply false
    kotlin("android") version "1.8.21" apply false
    kotlin("plugin.serialization") version "1.8.21" apply false
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false
}

allprojects {
    apply(from = "$rootDir/ktlint.gradle.kts")
}

tasks.register("Delete", Delete::class) {
    delete(rootProject.buildDir)
}

buildscript {
    dependencies {
        classpath("com.android.tools:r8:8.2.4-dev")
    }
}
