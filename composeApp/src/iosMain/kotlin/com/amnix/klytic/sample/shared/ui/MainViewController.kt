package com.amnix.klytic.sample.shared.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.amnix.klytic.Configuration
import com.amnix.klytic.Klytic
import com.amnix.klytic.kermit.KlyticNapierConfiguration
import com.amnix.klytic.kermit.KlyticNapierPlatform
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    Napier.base(DebugAntilog())
    val klytic = Klytic.create(
        configuration = Configuration.builder()
            .isDebug(true)
            .addPlatform(
                KlyticNapierPlatform(
                    configuration = KlyticNapierConfiguration.Builder().build()
                )
            )
            .build()
    )
    return ComposeUIViewController { App(klytic) }
}