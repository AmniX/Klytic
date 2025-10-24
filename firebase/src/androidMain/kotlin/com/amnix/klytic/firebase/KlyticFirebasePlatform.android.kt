@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.amnix.klytic.firebase

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import com.amnix.klytic.KlyticEvent
import com.amnix.klytic.Platform
import com.amnix.klytic.Tracker
import com.amnix.klytic.UserProperties
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.analytics

actual class KlyticFirebasePlatform(
    private val context: Context,
    private val configuration: KlyticFirebaseConfiguration,
) : Platform, Tracker {
    val firebaseAnalytics = runCatching {
        FirebaseApp.getInstance(configuration.appName) // Check if app already initialized
    }.recoverCatching { throwable -> // else create new app
        FirebaseApp.initializeApp(
            context,
            configuration.toFirebaseOptions(),
            configuration.appName
        )
    }.fold(onSuccess = {
        Firebase.analytics
    }, onFailure = {
        error("Failed to initialize Firebase Analytics with error: $it")
    })

    actual override suspend fun track(event: KlyticEvent) {
        firebaseAnalytics.logEvent(event.name, event.params.toBundle())
    }

    private fun Map<String, Any?>.toBundle(): Bundle = bundleOf(*this.toList().toTypedArray())

    actual override fun setUserProperties(userProperties: UserProperties) {
        firebaseAnalytics.setUserId(userProperties.userId)
        userProperties.customProperties.forEach { (key, value) ->
            firebaseAnalytics.setUserProperty(key, value)
        }
    }

}