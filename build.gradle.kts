// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
    id("org.sonarqube") version "3.5.0.2730"
}
sonar {
    properties {
        property("sonar.projectKey", "Rick and Morty")
    }
}