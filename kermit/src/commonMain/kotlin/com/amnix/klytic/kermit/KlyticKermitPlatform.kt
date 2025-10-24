package com.amnix.klytic.kermit

import co.touchlab.kermit.Logger
import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker

class KlyticKermitPlatform(configuration: KlyticKermitConfiguration) : Platform, Tracker {
    private val logger = Logger.withTag(configuration.tag)
    override suspend fun track(event: KlyticEvent) {
        logger.d { "Logging Event ${event.name} with params ${event.params}" }
    }
}