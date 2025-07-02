plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ungdungchuyendoitiente"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ungdungchuyendoitiente"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Thêm thư viện Retrofit
    // Thêm thư viện Retrofit
    // Thêm thư viện Retrofit
    //
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Thêm thư viện OkHttp nếu bạn cần logging cho các request/response
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
}