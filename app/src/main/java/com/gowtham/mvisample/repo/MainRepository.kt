package com.gowtham.mvisample.repo

import com.gowtham.mvisample.api.ApiHelperImpl
import com.gowtham.mvisample.api.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl) {

   suspend fun getPassengers() = apiHelperImpl.getPassengers()
}