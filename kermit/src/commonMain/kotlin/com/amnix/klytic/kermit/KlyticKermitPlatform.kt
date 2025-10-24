package com.amnix.klytic.kermit

import co.touchlab.kermit.Logger
import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker

class KlyticKermitPlatform(
    private val configuration: KlyticKermitConfiguration
) : Platform, Tracker {
    override suspend fun track(event: KlyticEvent) {
        if (configuration.tag == null)
            Logger.d { "Logging Event ${event.name} with params ${event.params}" }
        else Logger.withTag(configuration.tag)
            .d { "Logging Event ${event.name} with params ${event.params}" }
    }
}