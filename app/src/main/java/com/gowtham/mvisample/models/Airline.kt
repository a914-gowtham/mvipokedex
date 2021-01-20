package com.gowtham.mvisample.models

import com.squareup.moshi.Json

data class Airline(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "country")
    val country: String = "",
    @Json(name = "logo")
    val logo: String = "",
    @Json(name = "slogan")
    val slogan: String = "",
    @Json(name = "head_quaters")
    val headQuarters: String = "",
    @Json(name = "website")
    val website: String = "",
    @Json(name = "established")
    val established: String = ""
)