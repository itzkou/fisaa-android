package com.kou.fisaa.data.entities

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "advertisement")
data class Advertisement(
    @NonNull
    @PrimaryKey
    val _id: String,
    val arivalDate: String? = null,
    @Embedded(prefix = "user")
    val createdBy: AdCreator,
    val departure: String,
    val departureDate: String? = null,
    val destination: String,
    val type: String,
    @Embedded(prefix = "parcel")
    val parcel: Parcel? = null
)