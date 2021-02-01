package com.gowtham.mvipokedex.screens.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gowtham.mvipokedex.repo.MainRepository
import com.gowtham.mvipokedex.util.MainIntent
import com.gowtham.mvipokedex.util.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
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
        fetchPokemons()
    }

    private fun fetchPokemons() {
        viewModelScope.launch {
            mainIntent.send(MainIntent.FetchPokemons)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect { it ->
                when (it) {
                    is MainIntent.FetchPassengers -> {
//                     fetchPassengers()
                    }
                    is MainIntent.FetchPokemons ->{
                        repository.getPokemonListAsFlow().cachedIn(viewModelScope).collect { pagingData->
                            _state.value= MainState.Success(pagingData)
                        }
                    }
                }
            }
        }
    }

}