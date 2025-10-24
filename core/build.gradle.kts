import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "com.amnix.klytic"
version = "1.0.0"

kotlin {
    androidLibrary {
        namespace = "com.amnix.klytic.core"
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
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
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
        name = "Klytic-core"
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
