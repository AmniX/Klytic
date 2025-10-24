import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.kotlinCocoapods)
}

group = "com.amnix.klytic"
version = "1.0.0"

kotlin {
    androidLibrary {
        namespace = "com.amnix.klytic.firebase"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "KlyticFirebase"
            isStatic = true
            export(projects.core)
            binaryOption("bundleId","com.amnix.klytic.firebase")
            xcf.add(this)
        }
    }

    cocoapods {
        version = "1.0"
        summary = "A platform-agnostic analytics library built with Kotlin Multiplatform."
        homepage = "https://github.com/AmniX/Klytic"
        name = "KlyticFirebase"
        ios.deploymentTarget = "16.0"
        framework {
            isStatic = true
        }
        pod("FirebaseAnalytics") {
            version = "12.4.0"
        }
        pod("FirebaseCore") {
            version = "12.4.0"
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
        commonMain.dependencies {
            api(projects.core)
            implementation(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            implementation(libs.firebase.analytics)
            implementation(libs.core.ktx)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates(group.toString(), "klytic", version.toString())

    pom {
        name = "Klytic-firebase"
        description = "A platform-agnostic analytics library built with Kotlin Multiplatform."
        inceptionYear = "2025"
        url = "https://github.com/AmniX/Klytic"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "AmniX"
                name = "Aman Tonk"
                url = "https://github.com/AmniX"
            }
        }
        scm {
            url = "https://github.com/AmniX/Klytic"
            connection = "scm:git:git://github.com/AmniX/Klytic.git"
            developerConnection = "scm:git:ssh://github.com/AmniX/Klytic.git"
        }
    }
}
