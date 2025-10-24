package com.amnix.klytic.kermit

import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker
import io.github.aakira.napier.Napier

class KlyticNapierPlatform(
    private val configuration: KlyticNapierConfiguration
) : Platform, Tracker {
    override suspend fun track(event: KlyticEvent) {
        if (configuration.tag == null)
            Napier.d { "Logging Event ${event.name} with params ${event.params}" }
        else
            Napier.d(
                tag = configuration.tag,
                message = "Logging Event ${event.name} with params ${event.params}"
            )
    }
}