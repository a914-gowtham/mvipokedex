package com.gowtham.mvipokedex.api

import com.gowtham.mvipokedex.models.PokemonDetail
import com.gowtham.mvipokedex.models.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon/{name}")
    suspend fun fetchPokemonInfo(
        @Path("name") name: String
    ): Response<PokemonDetail>

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): Response<PokemonResponse>

}