package com.gowtham.mvipokedex.models

import com.squareup.moshi.Json

data class ApiResult(
    @Json(name = "totalPassengers")
    var totalPassengers: Int = 0,
    @Json(name = "totalPages")
    var totalPages: Int = 0,
    @field:Json(name = "data")
    var passengers: List<Passenger>
)