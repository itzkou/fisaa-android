package com.kou.fisaa.data.entities

data class Trip(
    val _id: String,
    val createdAt: String,
    val createdBy: AdCreator,
    val departure: String,
    val departureDate: String,
    val destination: String,
    val type: String
)