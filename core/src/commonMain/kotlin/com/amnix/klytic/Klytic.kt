package com.amnix.klytic

import com.amnix.klytic.internal.KlyticImpl
import kotlin.reflect.KClass

interface Klytic {
    companion object {
        fun create(configuration: Configuration): Klytic = KlyticImpl(configuration)
    }

    fun setUserProperties(userProperties: UserProperties)

    fun track(event: KlyticEvent, excludePlatform: List<KClass<out Platform>> = emptyList())
}