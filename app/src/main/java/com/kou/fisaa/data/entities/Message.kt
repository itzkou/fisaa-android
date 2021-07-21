package com.kou.fisaa.data.entities

data class Message(
    val fromId: String,
    val toId: String,
    val text: String,
    val senderPhoto: String,
    val senderName: String,
    val image: String,
    var parcel: Parcel?,
    val timeStamp: Long
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        null,
        -1
    )


}