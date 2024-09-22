plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.demax.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.demax.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.activity)
    implementation(libs.compose.icons.extended)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.kotlinx.serialization)
    implementation(project.dependencies.platform(libs.koin.bom))

    implementation(projects.core)
    implementation(projects.featureAuthorization)
    implementation(projects.featureDestructionDetails)
    implementation(projects.featureDestructionEdit)
    implementation(projects.featureDestructions)
    implementation(projects.featureHelpHistory)
    implementation(projects.featureProfile)
    implementation(projects.featureResourceDetails)
    implementation(projects.featureResourceEdit)
    implementation(projects.featureResourceHelp)
    implementation(projects.featureResources)
    implementation(projects.featureResponses)
}