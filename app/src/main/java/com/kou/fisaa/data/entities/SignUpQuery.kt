package com.kou.fisaa.data.entities

data class SignUpQuery(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val adress: String? = null,
    val cin: Int? = null,
    val city: String? = null,
    val country: String? = null,
    val dateOfBirth: String? = null,
    val description: String? = null,
    val phoneNumber: Long? = null,
    val zipCode: Int? = null
)