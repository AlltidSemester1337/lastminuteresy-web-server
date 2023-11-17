package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class BookingRequest(val integrationId: Int, val time: String)
