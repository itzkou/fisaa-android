package com.kou.fisaa.data.entities

data class Parcel(
    val _id: String,
    val bonus: Int,
    val description: String,
    val dimension: String,
    val parcelType: String,
    val photo: String,
    val weight: String
) {
    constructor() : this(
        "",
        0,
        "",
        "",
        "",
        "",
        ""
    )

}