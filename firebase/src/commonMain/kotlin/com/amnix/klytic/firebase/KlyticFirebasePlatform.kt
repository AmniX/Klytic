package com.amnix.klytic.firebase

import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker

expect class KlyticFirebasePlatform : Platform, Tracker {
    override suspend fun track(event: KlyticEvent)
}