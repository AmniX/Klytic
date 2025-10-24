package com.amnix.klytic.firebase

import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker
import com.amnix.klytic.UserProperties

expect class KlyticFirebasePlatform : Platform, Tracker {
    override suspend fun track(event: KlyticEvent)

    override fun setUserProperties(userProperties: UserProperties)
}