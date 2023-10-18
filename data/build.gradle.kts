plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.curtesmalteser.popularmovies.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 28

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

    implementation(project(":core"))
    implementation("com.google.code.gson:gson:2.8.9")

    val retrofitDependencies = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitDependencies")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitDependencies")

    testImplementation("junit:junit:4.13.2")

}