package com.kou.fisaa.data.entities

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "flight")
data class Flight(
    @NonNull
    @PrimaryKey
    val _id: String,
    var viewType: Int = 0,
    val createdAt: String? = null,
    val departure: String? = null,
    val departureDate: String? = null,
    val destination: String? = null,
    val type: String? = null,
    val updatedAt: String? = null,
    @Embedded(prefix = "user")
    val createdBy: FlightCreator?,
    val count: Int? = null,
    val place: String? = null
)