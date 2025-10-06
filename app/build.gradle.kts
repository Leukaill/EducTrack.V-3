plugins {
    id("com.android.application")
}

android {
    namespace = "com.equipe7.eductrack"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.equipe7.eductrack"
        minSdk = 26
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // 🔹 AndroidX / Material
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.activity:activity:1.8.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.github.lzyzsd:circleprogress:1.2.1")

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")   // Graphiques
    implementation ("com.google.firebase:firebase-firestore:24.9.1")// Firestore
    implementation ("com.google.firebase:firebase-functions")

    // 🔹 Picasso (images)
    implementation("com.squareup.picasso:picasso:2.8")

    // ✅ Google Mobile Ads (AdMob)
    implementation("com.google.android.gms:play-services-ads:22.6.0")

    // ✅ (optionnel si tu utilises Jetpack Compose)
    implementation("androidx.compose.ui:ui-text-google-fonts:1.9.0")
    implementation("androidx.compose.ui:ui-text:1.5.4")

    // ✅ Firebase BoM - centralise les versions Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))

    // ✅ Firebase services
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging") // 🔔 Notifications push
    implementation("com.google.firebase:firebase-database")  // 🔹 si tu veux Realtime DB

    // ✅ EmailJS (HTTP client pour envoyer via API EmailJS)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // ✅ Dots Indicator pour ViewPager2
    implementation("com.tbuonomo:dotsindicator:4.3")

    // ✅ Google Identity Services (nouvelle API login Google)
    implementation("androidx.credentials:credentials:1.2.2")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    // 🔹 Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

// ⚠️ Le plugin Firebase doit être appliqué APRÈS les dépendances
apply(plugin = "com.google.gms.google-services")
