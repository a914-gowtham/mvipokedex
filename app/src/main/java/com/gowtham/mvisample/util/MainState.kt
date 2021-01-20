package com.gowtham.mvisample.util

sealed class MainState {

    object Idle : MainState()

    object Loading : MainState()

    data class Success(val data: Any?=null) : MainState()

    data class Failure(val error: String?) : MainState()
}
