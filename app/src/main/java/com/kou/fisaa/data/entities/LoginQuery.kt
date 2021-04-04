package com.kou.fisaa.data.entities

data class LoginQuery(
    val email: String,
    val password: String? = null,
    val social: Boolean = false,
    val image: String? = null,
    val firstName: String? = null,
    val lastName: String? = null

)