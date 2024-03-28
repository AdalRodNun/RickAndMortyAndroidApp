plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.myapp.rickandmorty"
    compileSdk = Configuration.compileSdkVersion

    defaultConfig {
        applicationId = "com.myapp.rickandmorty"
        minSdk = Configuration.minSdkVersion
        targetSdk = Configuration.targetSdkVersion
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Room
    implementation(Libraries.ROOM_RUNTIME)
    implementation(Libraries.ROOM_KTX)

    // Retrofit
    implementation(Libraries.LOGGING_INTERCEPTOR)
    implementation(Libraries.RETROFIT)
    implementation(Libraries.CONVERTER_GSON)
    implementation(Libraries.GSON)

    // Core
    implementation(Libraries.CORE)
    implementation(Libraries.APP_COMPAT)

    // Design
    implementation(Libraries.MATERIAL_DESIGN)
    implementation(Libraries.LOTTIE)
    implementation(Libraries.GLIDE)
    kapt(Libraries.GLIDE_COMPILER)
    implementation(Libraries.SPLASH_SCREEN)

    // Architecture
    implementation(Libraries.ACTIVITY_KTX)
    implementation(Libraries.FRAGMENT_KTX)
    implementation(Libraries.VIEWMODEL)
    implementation(Libraries.LIVE_DATA)

    // Hashing
    implementation(Libraries.JBCRYPT)

    // Paging3
    implementation(Libraries.PAGING3)

    // Dagger - Hilt
    implementation(Libraries.DAGGER_HILT)
    kapt(Libraries.DAGGER_HILT_COMPILER)

    // Navigation
    implementation(Libraries.NAVIGATION_FRAGMENT)
    implementation(Libraries.NAVIGATION_UI)

    // Test
    testImplementation(Libraries.JUNIT)
    testImplementation(Libraries.MOCKK)
    testImplementation(Libraries.TRUTH)
    testImplementation(Libraries.COROUTINES_TEST)
    testImplementation(Libraries.CORE_TESTING)

    // Android Test
    androidTestImplementation(Libraries.JUNIT_TEST)
    androidTestImplementation(Libraries.ESPRESSO_CORE)
    androidTestImplementation(Libraries.NAVIGATION_TESTING)

    // Modules
    implementation(project(Modules.core))
    implementation(project(Modules.style))
    implementation(project(Modules.utilities))
}