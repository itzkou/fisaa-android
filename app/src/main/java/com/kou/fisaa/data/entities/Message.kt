package com.kou.fisaa.data.entities

import java.util.*

data class Message(
    //val id: String,
    val fromId: String,
    val toId: String,
    val text: String,
    val senderPhoto: String,
    val senderName: String,
    val timeStamp: Long
) {
    constructor() : this("", "", "", "", "", -1)

    fun timestampDate() = Date(timeStamp)
}