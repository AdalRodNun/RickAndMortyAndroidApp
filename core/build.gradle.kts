plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.myapp.rickandmorty.core"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

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

    implementation(Libraries.core)
    implementation(Libraries.appCompat)

    implementation(Libraries.roomKtx)
    implementation(Libraries.roomRuntime)
    kapt(Libraries.roomCompiler)

    implementation(Libraries.loggingInterceptor)
    implementation(Libraries.retrofit)
    implementation(Libraries.converterGson)

    testImplementation(Libraries.junit)
    androidTestImplementation(Libraries.junitTest)
    androidTestImplementation(Libraries.espressoCore)

    implementation(project(":utils"))
}