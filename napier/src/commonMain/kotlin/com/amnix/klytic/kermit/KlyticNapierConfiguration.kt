package com.amnix.klytic.kermit

class KlyticNapierConfiguration private constructor(
    internal val tag: String
) {
    class Builder {
        private var tag: String = "Klytic"
        fun setTag(tag: String) = apply { this.tag = tag }
        fun build() = KlyticNapierConfiguration(tag)
    }
}