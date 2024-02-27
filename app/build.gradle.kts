import Libraries.activityKtx
import Libraries.appCompat
import Libraries.constraintLayout
import Libraries.converterGson
import Libraries.core
import Libraries.espressoCore
import Libraries.fragmentKtx
import Libraries.gson
import Libraries.junit
import Libraries.junitTest
import Libraries.liveData
import Libraries.loggingInterceptor
import Libraries.materialDesign
import Libraries.retrofit
import Libraries.roomCompiler
import Libraries.roomKtx
import Libraries.roomRuntime
import Libraries.viewModel

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.myapp.rickandmorty"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.myapp.rickandmorty"
        minSdk = 26
        targetSdk = 33
        versionCode = 2
        versionName = "1.1.0"

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
    implementation(roomRuntime)
    implementation(roomKtx)
    kapt(roomCompiler)

    // Retrofit
    implementation(loggingInterceptor)
    implementation(retrofit)
    implementation(converterGson)
    implementation(gson)

    implementation(core)
    implementation(appCompat)

    implementation(materialDesign)
    implementation(constraintLayout)

    implementation("com.squareup.picasso:picasso:2.71828")

    implementation(activityKtx)
    implementation(fragmentKtx)
    implementation(viewModel)
    implementation(liveData)

    testImplementation(junit)
    androidTestImplementation(junitTest)
    androidTestImplementation(espressoCore)

    implementation(project(":utils"))
}