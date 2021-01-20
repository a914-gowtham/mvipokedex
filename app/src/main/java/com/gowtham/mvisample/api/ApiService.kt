package com.gowtham.mvisample.api

import com.gowtham.mvisample.models.ApiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("passenger")
    suspend fun getPassengers(
        @Query("page")
        pageNumber: Int = 0,
        @Query("size")
        size: Int = 10,
    ): Response<ApiResult>

}