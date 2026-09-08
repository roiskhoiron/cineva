import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    android {
       namespace = "io.codingskuy.cineva.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
       withDeviceTestBuilder {
           sourceSetTreeName = "test"
       }.configure {
           instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
    }
    
     sourceSets {
         androidMain.dependencies {
             implementation(libs.compose.uiToolingPreview)
             implementation(libs.compose.uiTooling)
             implementation(libs.ktor.client.okhttp)
             implementation(libs.sqldelight.android.driver)
         }
         iosMain.dependencies {
             implementation(libs.ktor.client.darwin)
             implementation(libs.sqldelight.native.driver)
         }
         commonMain.dependencies {
             implementation(libs.compose.runtime)
             implementation(libs.compose.foundation)
             implementation(libs.compose.material3)
             implementation(libs.compose.ui)
             implementation(libs.compose.components.resources)
             implementation(libs.compose.uiToolingPreview)
             implementation(libs.androidx.lifecycle.viewmodelCompose)
             implementation(libs.androidx.lifecycle.runtimeCompose)
             // KMP Clean Architecture deps - Fase 1 scaffold
             implementation(libs.kotlinx.serialization.json)
             implementation(libs.kotlinx.coroutines.core)
             implementation(libs.ktor.client.core)
             implementation(libs.ktor.client.contentNegotiation)
             implementation(libs.ktor.serialization.kotlinx.json)
             implementation(libs.ktor.client.logging)
             implementation(libs.sqldelight.runtime)
             implementation(libs.sqldelight.coroutines.ext)
             implementation(libs.voyager.navigator)
             implementation(libs.voyager.screenmodel)
             implementation(libs.koin.core)
             implementation(libs.coil.compose)
             implementation(libs.coil.network.ktor)
         }
         commonTest.dependencies {
             implementation(libs.kotlin.test)
             implementation(libs.kotlinx.coroutines.test)
             implementation(libs.turbine)
         }
     }

    // SQLDelight
    // will be configured via sqldelight{} block below
}

sqldelight {
    databases {
        create("CinevaDatabase") {
            packageName.set("io.codingskuy.cineva.db")
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}