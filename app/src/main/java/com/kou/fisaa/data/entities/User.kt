package com.kou.fisaa.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @NonNull
    @PrimaryKey
    val _id: String,
    val adress: String? = null,
    val cin: Int? = null,
    val city: String? = null,
    val country: String? = null,
    val dateOfBirth: String? = null,
    val description: String? = null,
    val email: String,
    val firstName: String,
    val lastName: String,
    val image: String? = null,
    val myTransactions: List<String?>,  // type converter needed if String was "Advertisement"
    val password: String,
    val phoneNumber: Long? = null,
    val publishedAdverts: List<String?>,
    val zipCode: Int? = null
) {
    constructor() : this("", "", 0, "", "", "", "", "", "", "", "", listOf(), "", 0L, listOf(), 0)
}