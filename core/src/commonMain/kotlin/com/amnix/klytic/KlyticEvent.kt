package com.amnix.klytic

data class KlyticEvent(
    val name: String,
    val values: Map<String, Any>
)