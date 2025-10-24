package com.amnix.klytic.firebase

import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker

actual class KlyticFirebasePlatform : Platform, Tracker {
    actual override suspend fun track(event: KlyticEvent) {
    }
}