plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
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
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)

    // Retrofit
    implementation(Libraries.loggingInterceptor)
    implementation(Libraries.retrofit)
    implementation(Libraries.converterGson)
    implementation(Libraries.gson)

    // Core
    implementation(Libraries.core)
    implementation(Libraries.appCompat)

    // Design
    implementation(Libraries.materialDesign)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.lottie)
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)
    implementation(Libraries.splashScreen)

    // Architecture
    implementation(Libraries.activityKtx)
    implementation(Libraries.fragmentKtx)
    implementation(Libraries.viewModel)
    implementation(Libraries.liveData)

    // Hashing
    implementation(Libraries.jbcrypt)

    // Paging3
    implementation(Libraries.paging3)

    // Dagger
    implementation(Libraries.daggerHilt)
    kapt(Libraries.daggerHiltCompiler)

    // Test
    testImplementation(Libraries.junit)
    androidTestImplementation(Libraries.junitTest)
    androidTestImplementation(Libraries.espressoCore)

    // Modules
    implementation(project(Modules.core))
    implementation(project(Modules.style))
    implementation(project(Modules.utilities))
}