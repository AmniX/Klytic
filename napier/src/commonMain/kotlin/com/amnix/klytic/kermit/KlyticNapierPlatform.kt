package com.amnix.klytic.kermit

import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker
import com.amnix.klytic.UserProperties
import io.github.aakira.napier.Napier

class KlyticNapierPlatform(
    private val configuration: KlyticNapierConfiguration
) : Platform, Tracker {
    override suspend fun track(event: KlyticEvent) {
        Napier.d(
            tag = configuration.tag,
            message = "Logging Event ${event.name} with params ${event.params}"
        )
    }

    override fun setUserProperties(userProperties: UserProperties) = Unit
}