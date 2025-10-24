package com.amnix.klytic.kermit

class KlyticKermitConfiguration private constructor(
    internal val tag: String
) {
    class Builder {
        private var tag: String = "Klytic"
        fun setTag(tag: String) = apply { this.tag = tag }
        fun build() = KlyticKermitConfiguration(tag)
    }
}