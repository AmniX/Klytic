package com.amnix.klytic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class Klytic private constructor(
    private val configuration: Configuration,
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    companion object {
        fun builder() = Configuration.Builder()
    }

    fun track(event: KlyticEvent, excludePlatform: List<KClass<out Platform>> = emptyList()) {
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