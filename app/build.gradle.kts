plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.subs_inter"
    compileSdk = 34
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.example.subs_inter"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.squareup.picasso:picasso:2.71828")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.android.gms:play-services-auth:20.2.0")
    implementation(libs.material)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1") // Adjust version as necessary
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1") // Adjust version as necessary
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1") // Adjust version as necessary
    implementation("androidx.lifecycle:lifecycle-common-java8:2.5.1") // This provides Java 8 API compatibility

    // Adding navigation dependencies
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1") // Adjust version as necessary
    implementation ("androidx.appcompat:appcompat:1.3.0")  // Check for the latest version
    implementation ("androidx.fragment:fragment-ktx:1.3.4") // For Kotlin support
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1") // Adjust version as necessary
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    implementation(libs.androidx.navigation.fragment.ktx)

    implementation ("androidx.camera:camera-core:1.1.0-alpha08")
    implementation ("androidx.camera:camera-camera2:1.1.0-alpha08")
    implementation ("androidx.camera:camera-lifecycle:1.1.0-alpha08")
    implementation ("androidx.camera:camera-view:1.0.0-alpha31")
    implementation ("androidx.camera:camera-camera2")
    implementation ("androidx.camera:camera-lifecycle:")
    implementation ("androidx.camera:camera-view:1.0.0-alpha20")
    implementation ("androidx.camera:camera-core:")
    ksp(libs.room.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.androidx.room.runtime)


}