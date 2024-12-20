plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "vn.edu.stu.oss_appdatvexemphim"
    compileSdk = 34

    defaultConfig {
        applicationId = "vn.edu.stu.oss_appdatvexemphim"
        minSdk = 28
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("org.projectlombok:lombok:1.18.36")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation ("com.google.android.libraries.places:places:2.6.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    compileOnly ("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok:1.18.36")

    implementation ("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")


//    implementation ("com.github.gastricspark:scrolldatepicker:0.0.1")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Espresso để hỗ trợ các hành động với RecyclerView
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.5.1")
    // JUnit để viết các bài kiểm thử
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    // AndroidX Test - các thư viện hỗ trợ kiểm thử
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test:rules:1.5.0")

}