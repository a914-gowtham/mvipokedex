package com.gowtham.mvipokedex.api

import com.gowtham.mvipokedex.models.PokemonDetail
import com.gowtham.mvipokedex.models.PokemonResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun fetchPokemonInfo(name: String): Response<PokemonDetail>

    suspend fun getPokemonList(offset: Int): Response<PokemonResponse>

}