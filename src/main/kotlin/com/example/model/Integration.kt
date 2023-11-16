package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Integration(val id: Int, val restaurant: String)
