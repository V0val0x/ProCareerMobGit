plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.procareermob"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.procareermob"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // Основные библиотеки Android
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // Jetpack Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.activity.compose)

    // AndroidX Navigation для Compose
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

    // Material3
    implementation(libs.androidx.material3.android)

    // Coil для загрузки изображений
    implementation(libs.coil.compose)

    // Retrofit и JSON конвертер с Kotlin Serialization
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)

    // Kotlin Coroutines для асинхронных задач
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Accompanist для FlowRow
    implementation(libs.accompanist.flowlayout)

    // Navigation для Compose
    implementation(libs.androidx.navigation.compose)

    // Accompanist для онбординга
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    // Accompanist System UI Controller
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.core.splashscreen)

    // Тестирование
    testImplementation(libs.junit)
    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(platform(libs.firebase.bom.v3211))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)
}