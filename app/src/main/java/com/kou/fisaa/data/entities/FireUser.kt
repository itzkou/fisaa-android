package com.kou.fisaa.data.entities


data class FireUser(
    var _id: String,
    var email: String,
    var firstName: String,
    var lastName: String,
    var image: String? = null
) {
    constructor() : this("", "", "", "")
}