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
    //val myTransactions: List<null>,  // type converter needed
    val password: String,
    val phoneNumber: Long? = null,
    //val publishedAdverts: List<null>,
    val zipCode: Int? = null
) {
    constructor() : this("", "", 0, "", "", "", "", "", "", "", "", "", 0L, 0)
}