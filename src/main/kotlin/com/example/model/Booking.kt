package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Booking(val restaurant: String, val time: String)
