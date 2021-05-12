package com.kou.fisaa.data.entities

data class Message(
    val id: String,
    val text: String,
    val fromId: String,
    val toId: String,
    val timeStamp: Long
) {
    constructor() : this("", "", "", "", -1)
}