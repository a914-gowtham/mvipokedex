package com.gowtham.mvipokedex.api

import com.gowtham.mvipokedex.models.PokemonDetail
import com.gowtham.mvipokedex.models.PokemonResponse
import retrofit2.Response
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService) : ApiHelper {

    override suspend fun fetchPokemonInfo(name: String): Response<PokemonDetail> {
       return apiService.fetchPokemonInfo(name)
    }

    override suspend fun getPokemonList(offset: Int): Response<PokemonResponse> {
        return apiService.getPokemonList(offset = offset)
    }
}