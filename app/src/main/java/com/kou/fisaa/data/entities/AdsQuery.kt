package com.kou.fisaa.data.entities

data class AdsQuery(

    val _id: String = "",
    val arivalDate: String? = "",
    val createdBy: String,
    val departure: String,
    val departureDate: String,
    val destination: String,
    val type: String,
    val parcel: Parcel? = null
)