plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.myapp.rickandmorty.style"
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

    // Design
    implementation(Libraries.MATERIAL_DESIGN)
    implementation(Libraries.SPLASH_SCREEN)

    // Test
    testImplementation(Libraries.JUNIT)
    androidTestImplementation(Libraries.JUNIT_TEST)
    androidTestImplementation(Libraries.ESPRESSO_CORE)
}