plugins {
<<<<<<< HEAD
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
=======
    id("com.android.application")
    id("com.google.gms.google-services")  // Aplica el plugin de google-services
>>>>>>> login
}

android {
    namespace = "com.example.appstronomyv2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appstronomyv2"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
<<<<<<< HEAD

=======
>>>>>>> login
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.room.common)
<<<<<<< HEAD

    implementation(libs.room.runtime)
    implementation(libs.firebase.auth)
=======
    implementation(libs.room.runtime)
    implementation(libs.firebase.auth)
    implementation(libs.activity)
>>>>>>> login
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    annotationProcessor(libs.room.compiler)
<<<<<<< HEAD
}
=======
    implementation(libs.firebase.analytics)
    implementation(platform(libs.firebase.bom))
}
>>>>>>> login
