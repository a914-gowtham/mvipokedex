package com.gowtham.mvisample.api

import com.gowtham.mvisample.models.ApiResult
import retrofit2.Response
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService) : ApiHelper {

    override suspend fun getPassengers(): Response<ApiResult> {
       return apiService.getPassengers()
    }
}