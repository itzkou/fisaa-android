package com.kou.fisaa.data.entities

data class SignUpQuery (
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val adress: String?,
    val cin: Int?,
    val city: String?,
    val country: String?,
     val dateOfBirth: String?,
    val description: String?,
    val phoneNumber: Long?,
     val zipCode: Int?
        )