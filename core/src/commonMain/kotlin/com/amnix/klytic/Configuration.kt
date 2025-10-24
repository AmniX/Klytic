package com.amnix.klytic

class Configuration private constructor(
    internal val isDebug: Boolean = false,
    internal val platforms: List<Platform> = emptyList()
) {
    class Builder {
        private var isDebug: Boolean = false
        private val platforms: MutableSet<Platform> = mutableSetOf()
        fun isDebug(isDebug: Boolean) = apply { this.isDebug = isDebug }
        fun addPlatform(platform: Platform) = apply { platforms.add(platform) }
        fun build() = Configuration(isDebug, platforms = platforms.toList())
    }
}