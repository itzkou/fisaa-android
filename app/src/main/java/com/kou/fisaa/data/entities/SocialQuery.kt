package com.kou.fisaa.data.entities

data class SocialQuery(
    val email: String,
    val firstName: String,
    val image: String,
    val lastName: String,
    val social: Boolean = true
)