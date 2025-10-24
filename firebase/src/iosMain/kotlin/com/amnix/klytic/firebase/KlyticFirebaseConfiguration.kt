package com.amnix.klytic.firebase

import cocoapods.FirebaseCore.FIROptions

class KlyticFirebaseConfiguration private constructor(
    internal val apiKey: String,
    internal val applicationId: String,
    internal val databaseUrl: String?,
    internal val gaTrackingId: String?,
    internal val gcmSenderId: String,
    internal val storageBucket: String?,
    internal val projectId: String?,
    internal val appName: String?,
) {

    internal fun toFirebaseOptions() = FIROptions(googleAppID = applicationId, GCMSenderID = gcmSenderId).apply {
        this.APIKey = apiKey
        this.projectID = projectId
        this.databaseURL = databaseUrl
        this@apply.storageBucket = this@KlyticFirebaseConfiguration.storageBucket
    }

    class Builder(private val apiKey: String, private val applicationId: String, private val gcmSenderId: String) {
        private var databaseUrl: String? = null
        private var gaTrackingId: String? = null
        private var storageBucket: String? = null
        private var projectId: String? = null
        private var appName: String? = null

        fun setDatabaseUrl(databaseUrl: String) = apply { this.databaseUrl = databaseUrl }

        fun setGaTrackingId(gaTrackingId: String) = apply { this.gaTrackingId = gaTrackingId }

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