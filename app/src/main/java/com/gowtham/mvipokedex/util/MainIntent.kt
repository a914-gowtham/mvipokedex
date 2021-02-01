package com.gowtham.mvipokedex.util

sealed class MainIntent {

    object FetchPassengers : MainIntent()

    object FetchPokemons : MainIntent()

    object FetchPokemonInfo : MainIntent()

}
