package com.kou.fisaa.data.entities

data class Message(
    //val id: String,
    val fromId: String,
    val toId: String,
    val text: String,
    val senderPhoto: String,
    val timeStamp: Long
) {
    constructor() : this("", "", "", "", -1)
}