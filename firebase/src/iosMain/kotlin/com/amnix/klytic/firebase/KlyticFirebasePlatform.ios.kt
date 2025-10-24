@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.amnix.klytic.firebase

import cocoapods.FirebaseAnalytics.FIRAnalytics
import cocoapods.FirebaseCore.FIRApp
import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker
import com.amnix.klytic.UserProperties

actual class KlyticFirebasePlatform(
    configuration: KlyticFirebaseConfiguration
) : Platform, Tracker {

    init {
        if (FIRApp.defaultApp() == null) {
            if (configuration.appName == null) {
                FIRApp.configureWithOptions(configuration.toFirebaseOptions())
            } else {
                FIRApp.configureWithName(configuration.appName, configuration.toFirebaseOptions())
            }
        }
    }

    actual override suspend fun track(event: KlyticEvent) {
        @Suppress("UNCHECKED_CAST") FIRAnalytics.logEventWithName(
            event.name,
            parameters = event.params as? Map<Any?, *>?
        )
    }

    actual override fun setUserProperties(userProperties: UserProperties) {
        FIRAnalytics.setUserID(userProperties.userId)
        userProperties.customProperties.forEach { (key, value) ->
            FIRAnalytics.setUserPropertyString(value, key)
        }
    }
}