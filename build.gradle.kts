// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        // other repositories...
        mavenCentral()

    }
    dependencies {
        // other plugins...
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")

    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}