package com.gowtham.mvipokedex.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gowtham.mvipokedex.repo.MainRepository
import com.gowtham.mvipokedex.util.MainIntent
import com.gowtham.mvipokedex.util.MainState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
    private val repository: MainRepository,
    @Assisted pokemonName: String,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mainIntent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainState>(MainState.Idle)

    private var pokemon= pokemonName

    val state: StateFlow<MainState>
        get() = _state

    @AssistedFactory
    interface Factory {
        fun create(pokemonName: String, savedStateHandle: SavedStateHandle): DetailViewModel
    }


    init {
        handleIntents()
        fetchPokemonInfo()
    }

    fun fetchPokemonInfo() {
        sendIntent(MainIntent.FetchPokemonInfo)
    }

    private fun sendIntent(intent: MainIntent){
        viewModelScope.launch {
            mainIntent.send(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect { it ->
                when (it) {
                    is MainIntent.FetchPokemonInfo -> {
                        _state.value=MainState.Loading
                        _state.value= try {
                            val response=repository.getPokemonDetail(pokemon)
                            MainState.Success(response.body())
                        } catch (e: Exception) {
                            MainState.Failure(e.localizedMessage)
                        }
                    }
                }
            }
        }
    }
}