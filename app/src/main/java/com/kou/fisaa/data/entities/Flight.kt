package com.kou.fisaa.data.entities

data class Flight(
    val _id: String,
    val viewType: Int,
    val createdAt: String? = null,
    val departure: String? = null,
    val departureDate: String? = null,
    val destination: String? = null,
    val type: String? = null,
    val updatedAt: String? = null

)