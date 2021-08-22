package com.kou.fisaa.data.entities

data class MyFlightsResponse(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val publishedAdverts: List<PublishedAdvert>
)