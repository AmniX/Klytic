package com.amnix.klytic.sample.shared.ui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform