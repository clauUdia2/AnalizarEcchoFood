plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.ecchofooddelivery"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ecchofooddelivery"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.database)

    // Dependencias para pruebas unitarias
    testImplementation(libs.junit)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.mockito:mockito-core:4.2.0")
    testImplementation("org.mockito:mockito-inline:4.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

    // Robolectric para ejecutar pruebas en la JVM
    testImplementation("org.robolectric:robolectric:4.10.3")  // Versión más reciente de Robolectric

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.github.bumptech.glide:glide:4.12.0")

    // Excluir módulos conflictivos de androidx.browser
    implementation("androidx.browser:browser:1.4.0") {
        exclude(group = "com.android.support", module = "customtabs")
    }

    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")

    implementation("com.facebook.android:facebook-android-sdk:[4,5)") {
        exclude(group = "com.android.support", module = "customtabs")
        exclude(group = "com.android.support", module = "support-compat")
    }
    implementation(kotlin("test"))
}
