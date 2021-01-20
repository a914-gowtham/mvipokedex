package com.gowtham.mvisample.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.mvisample.models.Passenger
import com.gowtham.mvisample.repo.MainRepository
import com.gowtham.mvisample.util.MainIntent
import com.gowtham.mvisample.util.MainState
import com.gowtham.mvisample.util.Utils
import com.gowtham.mvisample.util.Utils.fromJson
import com.gowtham.mvisample.util.Utils.toJson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Singleton


@Singleton
class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val mainIntent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainState>(MainState.Idle)

    val state: StateFlow<MainState>
        get() = _state

    init {
        handleIntents()
        fetchPassengers()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchPassengers -> {
                        fetchPassengers()
                    }
                }
            }
        }
    }

    fun send(intent: MainIntent){
        viewModelScope.launch {
            mainIntent.send(intent)
        }
        /*viewModelScope.launch(Dispatchers.Main,block = {

        })*/
    }

    private fun fetchPassengers() {
        viewModelScope.launch {
            _state.value=MainState.Loading
            _state.value=try {
                val s = repository.getPassengers()
               /* val str = "dfdsfd"
                var stdr = str.fromJson<Passenger>()*/
                Log.d("TAG", "hitApi: ${s.body()}")
                MainState.Success()
            } catch (e: Exception) {
                e.printStackTrace()
                MainState.Failure(e.localizedMessage)
            }

        }
    }

}