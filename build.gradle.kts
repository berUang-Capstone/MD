buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false //dengan asumsi versi Kotlin 1.9.0
    id("com.google.dagger.hilt.android") version "2.49" apply false
}


