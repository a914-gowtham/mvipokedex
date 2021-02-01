package com.gowtham.mvipokedex.models

import com.squareup.moshi.Json

data class Passenger(
    @Json(name = "_id")
    val _id: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "trips")
    val trips: Int = 0,
    @Json(name = "airline")
    var airlines: List<Airline>,
    @Json(name = "airline")
    var airline: Airline,
)