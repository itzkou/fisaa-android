package com.kou.fisaa.data.entities

data class Advertisement(
    val _id: String,
    var viewType: Int,
    val createdAt: String? = null,
    val departure: String? = null,
    val departureDate: String? = null,
    val destination: String? = null,
    val type: String? = null,
    val updatedAt: String? = null,
    val createdBy: User? = null,
)