package com.gowtham.mvisample.api

import com.gowtham.mvisample.models.ApiResult
import retrofit2.Response

interface ApiHelper {

    suspend fun getPassengers(): Response<ApiResult>

}