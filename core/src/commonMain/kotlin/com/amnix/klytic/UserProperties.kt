package com.amnix.klytic

data class UserProperties(
    val userId: String,
    val customProperties: Map<String, String?> = emptyMap()
)
