plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "feature-authorization"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.activity)
            implementation(project.dependencies.platform(libs.compose.bom))
            implementation(libs.compose.material3)
            implementation(libs.compose.navigation)
            implementation(libs.compose.ui)
            implementation(libs.compose.viewModel)
            implementation(libs.compose.ui.tooling.preview)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.android)
            implementation(libs.koin.compose)
        }
        commonMain.dependencies {
            implementation(libs.firebase.auth)
            implementation(libs.kotlinx.serialization)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(projects.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
}

android {
    namespace = "com.demax.feature.authorization"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
}
