package com.amnix.klytic.firebase

import com.google.firebase.FirebaseApp.DEFAULT_APP_NAME
import com.google.firebase.FirebaseOptions

class KlyticFirebaseConfiguration private constructor(
    internal val apiKey: String,
    internal val applicationId: String,
    internal val databaseUrl: String?,
    internal val gaTrackingId: String?,
    internal val gcmSenderId: String?,
    internal val storageBucket: String?,
    internal val projectId: String?,
    internal val appName: String,
) {

    internal fun toFirebaseOptions() = FirebaseOptions.Builder()
        .setApiKey(apiKey)
        .setApplicationId(applicationId)
        .setProjectId(projectId)
        .setDatabaseUrl(databaseUrl)
        .setGaTrackingId(gaTrackingId)
        .setGcmSenderId(gcmSenderId)
        .setStorageBucket(storageBucket)
        .build()

    class Builder(private val apiKey: String, private val applicationId: String) {
        private var databaseUrl: String? = null
        private var gaTrackingId: String? = null
        private var gcmSenderId: String? = null
        private var storageBucket: String? = null
        private var projectId: String? = null
        private var appName: String = DEFAULT_APP_NAME

        fun setDatabaseUrl(databaseUrl: String) = apply { this.databaseUrl = databaseUrl }

        fun setGaTrackingId(gaTrackingId: String) = apply { this.gaTrackingId = gaTrackingId }

        fun setGcmSenderId(gcmSenderId: String) = apply { this.gcmSenderId = gcmSenderId }

        fun setStorageBucket(storageBucket: String) = apply { this.storageBucket = storageBucket }

        fun setProjectId(projectId: String) = apply { this.projectId = projectId }

        fun setAppName(appName: String) = apply { this.appName = appName }

        fun build() = KlyticFirebaseConfiguration(
            apiKey = apiKey,
            applicationId = applicationId,
            databaseUrl = databaseUrl,
            gaTrackingId = gaTrackingId,
            gcmSenderId = gcmSenderId,
            storageBucket = storageBucket,
            projectId = projectId,
            appName = appName
        )
    }
}