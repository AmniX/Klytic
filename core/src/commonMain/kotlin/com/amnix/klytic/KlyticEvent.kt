package com.amnix.klytic

data class KlyticEvent(
    val name: String,
    val params: Map<String, Any>
)