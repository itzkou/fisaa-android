package com.kou.fisaa.data.entities

data class FlightSearchDatesQuery(
    val arivalDate: String,
    val departure: String,
    val departureDate: String,
    val destination: String
)