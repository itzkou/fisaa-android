package com.kou.fisaa.data.entities

data class AdCreator(
    val _id: String,
    val firstName: String = "",
    val image: String? = null,
    val lastName: String = ""
) {
    constructor() : this(
        "",
        "",
        "",
        ""
    )
}