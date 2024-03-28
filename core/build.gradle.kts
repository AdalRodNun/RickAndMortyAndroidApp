plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.myapp.rickandmorty.core"
    compileSdk = Configuration.compileSdkVersion

    defaultConfig {
        minSdk = Configuration.minSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
}

dependencies {

    // Core
    implementation(Libraries.CORE)
    implementation(Libraries.APP_COMPAT)

    // Room
    implementation(Libraries.ROOM_KTX)
    implementation(Libraries.ROOM_RUNTIME)
    kapt(Libraries.ROOM_COMPILER)

    // Retrofit
    implementation(Libraries.LOGGING_INTERCEPTOR)
    implementation(Libraries.RETROFIT)
    implementation(Libraries.CONVERTER_GSON)

    // Dagger
    implementation(Libraries.DAGGER_HILT)
    kapt(Libraries.DAGGER_HILT_COMPILER)

    // Test
    implementation(Libraries.DUPLICATED_ERROR_HANDLING)
    testImplementation(Libraries.JUNIT)

    // Test Android
    androidTestImplementation(Libraries.TRUTH)
    androidTestImplementation(Libraries.JUNIT_TEST)
    androidTestImplementation(Libraries.ESPRESSO_CORE)
    androidTestImplementation(Libraries.CORE_TESTING)

    // Modules
    implementation(project(Modules.utilities))
}