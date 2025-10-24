package com.amnix.klytic.sample.shared.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.amnix.klytic.Configuration
import com.amnix.klytic.Klytic
import com.amnix.klytic.firebase.KlyticFirebaseConfiguration
import com.amnix.klytic.firebase.KlyticFirebasePlatform
import com.amnix.klytic.kermit.KlyticNapierConfiguration
import com.amnix.klytic.kermit.KlyticNapierPlatform
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainActivity : ComponentActivity() {

    private val klytic by lazy {
        R.string.google_app_id
        Klytic.create(
            configuration = Configuration.builder()
                .isDebug(true)
                .addPlatform(
                    KlyticNapierPlatform(
                        configuration = KlyticNapierConfiguration.Builder().build()
                    )
                )
                .addPlatform(
                    KlyticFirebasePlatform(
                        context = this,
                        configuration = KlyticFirebaseConfiguration.Builder(
                            apiKey = "AIzaSyCKwqzYVjZ3ZEeLGGEvqa98jpqFm2cjipE",
                            applicationId = "1:660284652774:android:e0728858ed18e0c7e3cf27"
                        ).build()
                    )
                )
                .build()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Napier.base(DebugAntilog())
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(klytic)
        }
    }
}