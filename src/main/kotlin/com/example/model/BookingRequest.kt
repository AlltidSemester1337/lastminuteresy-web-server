package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class BookingRequest(val integrationId: Int, val numPersons: Int, val time: String, val extraParameters: Map<String, String>)
