package com.amnix.klytic

interface Tracker{
    suspend fun track(event: KlyticEvent)
}