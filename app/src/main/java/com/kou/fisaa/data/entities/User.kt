package com.kou.fisaa.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    val __v: Int?,
    @PrimaryKey
    val _id: String,
    val adress: String?,
    val cin: Int?,
    val city: String?,
    val country: String?,
    val createdAt: String?,
    val dateOfBirth: String?,
    val description: String?,
    val email: String,
    val firstName: String?,
    val lastName: String?,
    //val myTransactions: List<null>,  // type converter needed
    val password: String,
    val phoneNumber: Long?,
    //val publishedAdverts: List<null>,
    val updatedAt: String?,
    val zipCode: Int?
)