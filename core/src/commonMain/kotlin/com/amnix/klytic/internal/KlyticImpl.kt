package com.amnix.klytic.internal

import com.amnix.klytic.Configuration
import com.amnix.klytic.Klytic
import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker
import com.amnix.klytic.UserProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

internal class KlyticImpl(
    private val configuration: Configuration,
) : Klytic {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun setUserProperties(userProperties: UserProperties) {
        configuration.platforms.forEach { platform ->
            platform.setUserProperties(userProperties)
        }
    }

    override fun track(event: KlyticEvent, excludePlatform: List<KClass<out Platform>>) {
        coroutineScope.launch {
            if (excludePlatform.isEmpty()) {
                trackForAll(event)
            } else {
                trackExcept(event, excludePlatform)
            }
        }
    }

    private suspend fun trackExcept(
        event: KlyticEvent,
        excludePlatform: List<KClass<out Platform>>
    ) {
        configuration.platforms.filter { platform ->
            platform::class !in excludePlatform
        }.filterIsInstance<Tracker>().map { tracker ->
            trackFor(event, tracker)
        }.awaitAll()
    }

    private suspend fun trackForAll(event: KlyticEvent) {
        configuration.platforms.filterIsInstance<Tracker>().map { tracker ->
            trackFor(event, tracker)
        }.awaitAll()
    }

    private suspend fun trackFor(event: KlyticEvent, tracker: Tracker) = coroutineScope {
        async {
            runCatching {
                tracker.track(event)
            }.onFailure { throwable ->
                if (configuration.isDebug) {
                    println("Unable to track event $event to $tracker")
                    throwable.printStackTrace()
                }
            }
        }
    }
}